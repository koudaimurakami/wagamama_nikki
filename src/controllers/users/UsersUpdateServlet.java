package controllers.users;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.User;
import utils.DBUtil;
import utils.EncryptUtil;
import validators.UserValidator;

/**
 * Servlet implementation class UsersUpdateServlet
 */
@WebServlet("/users/update")
public class UsersUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersUpdateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String _token = request.getParameter("_token");
	        if(_token != null && _token.equals(request.getSession().getId())) {
	            EntityManager em = DBUtil.createEntityManager();

	            User u = em.find(User.class, (Integer)(request.getSession().getAttribute("user_id")));

	            // 現在の値と異なるログインIDが入力されていたら
	            // 重複チェックを行う指定をする
	            Boolean login_idDuplicateCheckFlag = true;
	            if(u.getLogin_id().equals(request.getParameter("login_id"))) {
	                login_idDuplicateCheckFlag = false;
	            } else {
	                u.setLogin_id(request.getParameter("login_id"));
	            }

	            // パスワード欄に入力があったら
	            // パスワードの入力値チェックを行う指定をする
	            Boolean passwordCheckFlag = true;
	            String password = request.getParameter("password");
	            if(password == null || password.equals("")) {
	                passwordCheckFlag = false;
	            } else {
	                u.setPassword(
	                        EncryptUtil.getPasswordEncrypt(
	                                password,
	                                (String)this.getServletContext().getAttribute("pepper")
	                                )
	                        );
	            }

	            u.setName(request.getParameter("name"));
	            u.setUpdated_at(new Timestamp(System.currentTimeMillis()));
	            u.setDelete_flag(0);

	            List<String> errors = UserValidator.validate(u, login_idDuplicateCheckFlag, passwordCheckFlag);
	            if(errors.size() > 0) {
	                em.close();

	                request.setAttribute("_token", request.getSession().getId());
	                request.setAttribute("user", u);
	                request.setAttribute("errors", errors);

	                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
	                rd.forward(request, response);
	            } else {
	                em.getTransaction().begin();
	                em.getTransaction().commit();    // データの更新を確定させる
	                em.close();
	                request.getSession().setAttribute("flush", "更新が完了しました。");

	                request.getSession().removeAttribute("user_id");

	                response.sendRedirect(request.getContextPath() + "/");
	            }
	        }
	}

}
