package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.User;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ProfileFieldToChange;
import by.urban.web_project.utils.UpdateUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeName implements Command {
    private final IChangeProfileService updateTool;

    public ChangeName() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //проверяем, жива ли сессия
        if (request.getSession(false) == null){
            request.setAttribute("errorMessage", "Вы не авторизованы.");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }

        //кастуем, потому что getAttribute возвращает Object
        //атрибут автора назначали при авторизации
        String newName = request.getParameter("newName");
        User user = (User) request.getSession().getAttribute("user");
        User author = (User) request.getSession().getAttribute("author");

        if (newName != null && !newName.trim().isEmpty()) {
            boolean updateSuccess = false;

            if (author != null) {
                updateSuccess = UpdateUtils.updateProfileField(author, newName, ProfileFieldToChange.NAME, updateTool); // Обновляем имя
            } else if (user != null) {
                updateSuccess = UpdateUtils.updateProfileField(user, newName, ProfileFieldToChange.NAME, updateTool); // Обновляем имя
            }

            // Обработка результата
            if (updateSuccess) {
                request.getSession().setAttribute("changeNameSuccess", "Имя успешно обновлено");
                if (author != null) {
                    request.getSession().setAttribute("author", author);
                    response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
                } else {
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
                }
            } else {
                request.getSession().setAttribute("changeNameError", "Произошла ошибка при обновлении имени");
                response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
            }
        } else {
            request.getSession().setAttribute("changeNameError", "Вы не задали новое имя");
            response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
        }
    }
}