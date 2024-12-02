package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.User;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ProfileFieldToChange;
import by.urban.web_project.utils.UpdateUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeBio implements Command {

    private final IChangeProfileService updateTool;

    public ChangeBio() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //проверяем, жива ли сессия
        if (request.getSession(false) == null){
            request.setAttribute("errorMessage", "Вы не авторизованы.");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }

        // Получаем пользователя из сессии
        User user = (User) request.getSession().getAttribute("author");
        String newValue = request.getParameter("newBio"); // Пример для биографии
        ProfileFieldToChange field = ProfileFieldToChange.BIO; // Указание, что обновляется биография

        if (newValue != null && !newValue.trim().isEmpty()) {
            boolean isUpdated = UpdateUtils.updateProfileField(user, newValue, field, updateTool);

            if (isUpdated) {
                request.getSession().setAttribute("newBio", newValue);
                request.getSession().setAttribute("changeBioSuccess", "Биография успешно обновлена");
            } else {
                request.getSession().setAttribute("changeBioError", "Произошла ошибка при обновлении биографии");
            }
        } else {
            request.getSession().setAttribute("changeBioError", "Вы не написали ничего в поле");
        }

        response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
    }}