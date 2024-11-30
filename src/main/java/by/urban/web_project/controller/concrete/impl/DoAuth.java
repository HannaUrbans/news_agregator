package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.mockdb.NewsDatabase;
import by.urban.web_project.model.News;
import by.urban.web_project.model.ProfileDataField;
import by.urban.web_project.model.User;
import by.urban.web_project.model.UserRole;
import by.urban.web_project.service.IAuthorizationService;
import by.urban.web_project.service.IProfileDataService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DoAuth implements Command {
    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final IAuthorizationService logicForAuthorization = serviceFactory.getAuthorizationService();
    private final IProfileDataService profileDataTool = serviceFactory.getProfileDataService();

    //idea заставила пробросить в конструкторе исключение из-за serviceFactory.getAuthorizationService()
    public DoAuth() throws ServiceException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            //проверяем формат email + авторизован ли посетитель
            User authorizedObject = logicForAuthorization.checkAuth(email, password);
            //общий атрибут у всех ролей, который впоследствии будет использоваться для уникальных действий для ВСЕХ залогинившихся ролей
            request.getSession().setAttribute("id", authorizedObject.getId());
            request.getSession().setAttribute("nameFromDb", authorizedObject.getName());

            switch (authorizedObject.getUserRole()) {
                case UserRole.ADMIN:
                    request.getSession(true).setAttribute("admin", authorizedObject);
                    System.out.println( "В сети " + authorizedObject);
                    response.sendRedirect("Controller?command=GO_TO_ADMIN_ACCOUNT_PAGE");
                    break;
                case UserRole.AUTHOR:
                    request.getSession(true).setAttribute("author", authorizedObject);
                    request.getSession().setAttribute("emailFromDb",  profileDataTool.getDataFromDatabase(authorizedObject.getId(), ProfileDataField.EMAIL));
                    request.getSession().setAttribute("passwordFromDb",  profileDataTool.getDataFromDatabase(authorizedObject.getId(), ProfileDataField.PASSWORD));
                    request.getSession().setAttribute("bioFromDb",  profileDataTool.getDataFromDatabase(authorizedObject.getId(), ProfileDataField.BIO));
                    List<News> authorNewsList = NewsDatabase.getNewsByAuthor(email);
                    Collections.reverse(authorNewsList);
                    request.getSession().setAttribute("authorNewsList", authorNewsList);
                    System.out.println( "В сети " + authorizedObject);
                    response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
                    break;
                case UserRole.USER:
                    request.getSession(true).setAttribute("user", authorizedObject);
                    request.getSession().setAttribute("emailFromDb",  profileDataTool.getDataFromDatabase(authorizedObject.getId(), ProfileDataField.EMAIL));
                    request.getSession().setAttribute("passwordFromDb",  profileDataTool.getDataFromDatabase(authorizedObject.getId(), ProfileDataField.PASSWORD));
                    System.out.println( "В сети " + authorizedObject);
                    response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
                    break;
            }

        } catch (ServiceException e) {
            e.printStackTrace();
            request.getSession().setAttribute("authError", "Произошла ошибка при авторизации");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }
    }
}
