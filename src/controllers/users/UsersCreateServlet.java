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
 * Servlet implementation class UsersCreateServlet
 */
@WebServlet("/users/create")
public class UsersCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UsersCreateServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 String _token = request.getParameter("_token");
	        if(_token != null && _token.equals(request.getSession().getId())) {
	            EntityManager em = DBUtil.createEntityManager();

	            // Userのインスタンスを生成
	            User u = new User();

	            u.setLogin_id(request.getParameter("login_id"));
	            u.setName(request.getParameter("name"));
	            u.setPassword(
	                EncryptUtil.getPasswordEncrypt(
	                    request.getParameter("password"),
	                        (String)this.getServletContext().getAttribute("pepper")
	                    )
	                );

	            Timestamp currentTime = new Timestamp(System.currentTimeMillis());
	            u.setCreated_at(currentTime);
	            u.setUpdated_at(currentTime);
	            u.setDelete_flag(0);

	            List<String> errors = UserValidator.validate(u, true, true);
	            if(errors.size() > 0) {
	                em.close();

	                request.setAttribute("_token", request.getSession().getId());
	                request.setAttribute("user", u);
	                request.setAttribute("errors", errors);

	                // エラーになった場合は、再度ユーザー登録画面に戻る ← 名前、ログインID、パスワード すべて必須入力事項なので、どれか一つでも入力されなかったらエラーとなる
	                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users/new.jsp");
	                rd.forward(request, response);
	            } else {
	                em.getTransaction().begin();
	                em.persist(u);                   // データベースに保存
	                em.getTransaction().commit();    // データの新規登録を確定させる
	                request.getSession().setAttribute("flush", "登録が完了しました。");
	                em.close();

	                response.sendRedirect(request.getContextPath() + "/");
	            }
	        }
	}

}
