package by.urban.web_project.utils;

import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import jakarta.servlet.http.HttpServletRequest;

//это для отладки
public class SessionUtils {
    public static void logCurrentVisitor(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");

        if (user != null) {
            System.out.println("В сессии пользователь: " + ((User) user).getName());
            System.out.println(request.getSession().getId());
        } else {
            Object author = request.getSession().getAttribute("author");
            if (author != null) {
                System.out.println("В сессии автор: " + ((Author) author).getName());
                System.out.println(request.getSession().getId());
            } else {
                System.out.println("Пользователь не авторизован");
            }
        }
    }
}
