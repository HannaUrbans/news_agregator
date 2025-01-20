package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.controller.utils.UrlFormatterUtil;
import by.urban.web_project.service.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.AuthPresenceUtil.checkAuthPresence;
import static by.urban.web_project.controller.utils.UpdateUtil.isProfileFieldCheckedAndUpdated;
import static by.urban.web_project.controller.utils.UpdateUtil.isProfileFieldUpdated;

//отдельно, на одну форму по одной команде контроллера + эта форма доступна для всех ролей, в отличие от changeBio

public class ChangeAccount implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServiceException {

        Auth auth = (Auth) request.getSession().getAttribute("auth");

        //проверяем, жива ли сессия
        checkAuthPresence(request, response, auth);

        boolean updated = false;
        updated = isProfileFieldUpdated(request, auth, request.getParameter("newName"), ProfileDataField.NAME);
        updated = isProfileFieldCheckedAndUpdated(request, auth, request.getParameter("oldEmail"), request.getParameter("newEmail"), ProfileDataField.EMAIL);
        updated = isProfileFieldCheckedAndUpdated(request, auth, request.getParameter("oldPassword"), request.getParameter("newPassword"), ProfileDataField.PASSWORD);

        // проверяем, было ли изменено хотя бы одно поле
        if (updated) {
            request.getSession().setAttribute("changeAccountSuccess", "Профиль успешно обновлен!");
        } else {
            request.getSession().setAttribute("changeAccountError", "Не было внесено ни одного изменения.");
        }

        // Перенаправление на страницу профиля
        UserRole role = UserRole.valueOf(((String) request.getSession().getAttribute("role")).toUpperCase());
        response.sendRedirect("Controller?command=" + UrlFormatterUtil.formatRedirectUrl(role));
    }
}