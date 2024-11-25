package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import by.urban.web_project.service.IAuthorizationService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.SessionUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DoAuth implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final IAuthorizationService logicForAuthorization = serviceFactory.getAuthorizationService();

    public DoAuth() throws DAOException, ServiceException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            System.out.println("Авторизация пользователя: " + email);

            //String hashedPassword = PasswordHasher.hashPasswordForRegistration(password);  // Хешируем введённый пароль

            //проверяем формат email, пробуем авторизоваться как автор или как юзер
            Object authorizedObject = logicForAuthorization.checkAuth(email, password);
            //Object authorizedObject = logicForAuthorization.checkAuth(email, hashedPassword);

            if (authorizedObject != null) {
                //приводим к классу Автор
                if (authorizedObject instanceof Author) {
                    Author author = (Author) authorizedObject;

                    request.getSession(true).setAttribute("author", author);
                    request.getSession(true).setAttribute("email", email);

                    List<News> authorNewsList = NewsDatabase.getNewsByAuthor(email);
                    Collections.reverse(authorNewsList);
                    System.out.println("authorNewsList size: " + authorNewsList.size());

                    request.getSession().setAttribute("authorNewsList", authorNewsList);
                    request.getSession().setAttribute("authorGetName", author.getName());

                    response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
                }
                // если не привели без проблем к классу Author, пробуем User
                else if (authorizedObject instanceof User) {
                    User user = (User) authorizedObject;

                    request.getSession(true).setAttribute("user", user);
                    request.getSession(true).setAttribute("email", email);
                    request.getSession().setAttribute("userGetName", user.getName());

                    response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
                }
            } else {
                request.getSession().setAttribute("authError", "Неправильный email или пароль");
                response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            }

            //выводим на консоль, кто авторизовался
            SessionUtils.logCurrentVisitor(request);

        } catch (ServiceException e) {
            e.printStackTrace();
            request.getSession().setAttribute("authError", "Произошла ошибка при авторизации");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }
    }
}
