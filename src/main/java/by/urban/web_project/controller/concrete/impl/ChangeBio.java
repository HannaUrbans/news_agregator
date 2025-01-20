package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.controller.utils.UrlFormatterUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.UpdateUtil.*;

public class ChangeBio implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");

        // если не в сессии
        // если от другой роли мы проверяем шагом ранее (в контроллере GoToChangeForm), иначе на страницу заходило, только не давало отправить
        checkAuthPresence(request, response, auth);

        boolean isProfileUpdated = isProfileFieldUpdated(request, auth, request.getParameter("newBio"), ProfileDataField.BIO);
        updateSessionAndDisplayMessage(request, isProfileUpdated, auth, ProfileDataField.BIO);

        // Перенаправление на страницу профиля
        response.sendRedirect("Controller?command=" + UrlFormatterUtil.formatRedirectUrl(UserRole.valueOf(((String) request.getSession().getAttribute("role")).toUpperCase())));
    }
}