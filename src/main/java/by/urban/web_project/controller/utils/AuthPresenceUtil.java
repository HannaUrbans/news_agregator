package by.urban.web_project.controller.utils;

import by.urban.web_project.bean.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthPresenceUtil {
    public static void checkAuthPresence(HttpServletRequest request, HttpServletResponse response, Auth auth) {
        // если не в сессии
        try {
            if (auth == null) {
                System.out.println("Пользователь не залогинен и пытается открыть скрытую для неавторизованного пользователя страницу");
                request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
                response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
