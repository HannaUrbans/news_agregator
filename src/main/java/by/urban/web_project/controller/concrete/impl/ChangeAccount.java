package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.controller.utils.UpdateUtil;
import by.urban.web_project.controller.utils.UrlFormatterUtil;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ProfileFieldToChange;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeAccount implements Command {
    private final IChangeProfileService changeProfileService;
    private final ICheckService checkService;

    public ChangeAccount() throws ServiceException {
        this.changeProfileService = ServiceFactory.getInstance().getChangeProfileService();
        this.checkService = ServiceFactory.getInstance().getCheckService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //проверяем, жива ли сессия
        if (request.getSession(false) == null) {
            request.setAttribute("errorMessage", "Вы не авторизованы.");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }

        //кастуем, потому что getAttribute возвращает Object
        Auth auth = (Auth) request.getSession().getAttribute("auth");
        String role = (String) request.getSession().getAttribute("role");
        int id = (int) request.getSession().getAttribute("id");
        String name = (String) request.getSession().getAttribute("name");

        if (auth == null) {
            request.setAttribute("errorMessage", "Не удалось получить данные о пользователе из сессии.");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
        }
        // Получаем данные из формы
        String newName = request.getParameter("newName");
        String newEmail = request.getParameter("newEmail");
        String newPassword = request.getParameter("newPassword");
        String oldEmail = request.getParameter("oldEmail");
        String oldPassword = request.getParameter("oldPassword");

        boolean updated = false;

        // Проверка и обновление имени
        if (newName != null && !newName.trim().isEmpty()) {
            try{
            if (checkService.checkFieldsEquality(name, newName)) {
                //подумай мб лучше не обновлять, а выписать ошибку и редирект (но тогда надо проверить, передадутся ли остальные измененные поля)
                request.getSession().setAttribute("changeEmailError", "Внимание, старое имя совпадает с новым именем");
            }

            boolean updateName = UpdateUtil.updateProfileField(auth, newName, ProfileFieldToChange.NAME, changeProfileService);
            if (updateName) {
                updated = true;
            } else {
                request.getSession().setAttribute("changeNameError", "Произошла ошибка при обновлении имени");
            }} catch (ServiceException e) {
                e.printStackTrace();
            }
        }

        // Проверка и обновление email
        if (newEmail != null && !newEmail.trim().isEmpty()) {
            String emailFromDb = null;
            try {
                emailFromDb = changeProfileService.getFieldData(id, ProfileDataField.EMAIL);

                if (checkService.checkFieldsEquality(emailFromDb, oldEmail)) {
                    if (checkService.checkFieldsEquality(oldEmail, newEmail)) {
                        //подумай мб лучше не обновлять, а выписать ошибку и редирект (но тогда надо проверить, передадутся ли остальные измененные поля)
                        request.getSession().setAttribute("changeEmailError", "Внимание, старый email совпадает с новым email");
                    }

                    boolean updateEmail = UpdateUtil.updateProfileField(auth, newEmail, ProfileFieldToChange.EMAIL, changeProfileService);
                    if (updateEmail) {
                        updated = true;
                    } else {
                        request.getSession().setAttribute("changeEmailError", "Произошла ошибка при обновлении email");
                    }
                } else {
                    request.getSession().setAttribute("changeEmailError", "Старый email не совпадает с email из личного кабинета");
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }

        // Проверка и обновление пароля
        if (newPassword != null && !newPassword.trim().isEmpty()) {
            String passwordFromDb = null;
            try {
                passwordFromDb = changeProfileService.getFieldData(id, ProfileDataField.PASSWORD);

                if (checkService.checkFieldsEquality(passwordFromDb, oldPassword)) {
                    if (checkService.checkFieldsEquality(oldPassword, newPassword)) {
                        //подумай мб лучше не обновлять, а выписать ошибку и редирект (но тогда надо проверить, передадутся ли остальные измененные поля)
                        request.getSession().setAttribute("changePasswordError", "Внимание, старый пароль совпадает с новым паролем");
                    }

                    boolean updatePassword = UpdateUtil.updateProfileField(auth, newPassword, ProfileFieldToChange.PASSWORD, changeProfileService);
                    if (updatePassword) {
                        updated = true;
                    } else {
                        request.getSession().setAttribute("changePasswordError", "Произошла ошибка при обновлении пароля");
                    }
                } else {
                    request.getSession().setAttribute("changePasswordError", "Старый пароль не совпадает с паролем из личного кабинета");
                }
            } catch (ServiceException e) {
                e.printStackTrace();
            }
        }

        // Проверка, были ли изменения
        if (updated) {
            request.getSession().setAttribute("changeAccountSuccess", "Профиль успешно обновлен!");
        } else {
            request.getSession().setAttribute("changeAccountError", "Не было внесено ни одного изменения.");
        }

        // Перенаправление на страницу профиля
        response.sendRedirect("Controller?command=" + UrlFormatterUtil.formatRedirectUrl(role));
    }
}