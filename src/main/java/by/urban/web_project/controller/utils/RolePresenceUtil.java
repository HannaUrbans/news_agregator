package by.urban.web_project.controller.utils;

import by.urban.web_project.model.Auth;
import by.urban.web_project.model.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class RolePresenceUtil {
    public static boolean isAuthRoleValid(HttpServletRequest request, HttpServletResponse response, UserRole role) {
        Auth auth = (Auth) request.getSession(false).getAttribute("auth");

        try {
            if (!role.equals(auth.getRole())) {
                System.out.println("Пользователь пытается войти на страницу, закрытую для его роли");
                request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
                response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
