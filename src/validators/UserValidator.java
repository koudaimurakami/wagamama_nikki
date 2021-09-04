package validators;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import models.User;
import utils.DBUtil;

public class UserValidator {
	 public static List<String> validate(User u, Boolean login_idDuplicateCheckFlag, Boolean passwordCheckFlag) {
	        List<String> errors = new ArrayList<String>();

	        String login_id_error = validateLoginId(u.getLogin_id(), login_idDuplicateCheckFlag);
	        if(!login_id_error.equals("")) {
	            errors.add(login_id_error);
	        }

	        String name_error = validateName(u.getName());
	        if(!name_error.equals("")) {
	            errors.add(name_error);
	        }

	        String password_error = validatePassword(u.getPassword(), passwordCheckFlag);
	        if(!password_error.equals("")) {
	            errors.add(password_error);
	        }

	        return errors;
	    }

	    // ログインID
	    private static String validateLoginId(String login_id, Boolean loginIdDuplicateCheckFlag) {
	        // 必須入力チェック
	        if(login_id == null || login_id.equals("")) {
	            return "ログインIDを入力してください。";
	        }

	        // すでに登録されているログインIDとの重複チェック
	        if(loginIdDuplicateCheckFlag) {
	            EntityManager em = DBUtil.createEntityManager();
	            long user_count = (long)em.createNamedQuery("checkRegisteredLoginId", Long.class).setParameter("login_id", login_id).getSingleResult();
	            em.close();
	            if(user_count > 0) {
	                return "入力されたログインIDの情報はすでに存在しています。";
	            }
	        }

	        return "";
	    }

	    // ユーザー名の必須入力チェック
	    private static String validateName(String name) {
	        if(name == null || name.equals("")) {
	            return "ユーザー名を入力してください。";
	        }

	        return "";
	    }

	    // パスワードの必須入力チェック
	    private static String validatePassword(String password, Boolean passwordCheckFlag) {
	        // パスワードを変更する場合のみ実行
	        if(passwordCheckFlag && (password == null || password.equals(""))) {
	            return "パスワードを入力してください。";
	        }
	        return "";
	    }
}
