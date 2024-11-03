package by.urban.web_project.utils;

import by.urban.web_project.bean.User;
import jakarta.servlet.http.HttpServletRequest;

public class SessionUtils {
	public static void logCurrentUser(HttpServletRequest request) {
		User currentUser = (User) request.getSession().getAttribute("user");
		if (currentUser != null) {
			System.out.println("Текущий пользователь: " + currentUser.getName());
		} else {
			System.out.println("Пользователь не авторизован");
		}
	}
}
