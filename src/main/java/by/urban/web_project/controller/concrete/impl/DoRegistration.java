package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.IRegistrationService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DoRegistration implements Command {

    private final ServiceFactory serviceFactory = ServiceFactory.getInstance();
    private final IRegistrationService logicForRegistration = serviceFactory.getRegistrationService();
    private final ICheckService check = serviceFactory.getCheckService();

    public DoRegistration() throws ServiceException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Параметры приходят из формы регистрации
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String regKey = request.getParameter("authorKey");
            String password = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            // Прописывается в личном кабинете, а не при регистрации
            String bio = "";

            if (detectErrorsInRegistrationData(request, response, email, password, confirmPassword)) {
                return;
            }

            // Проверяем поле "ключ автора" при наличии данных внутри него
            if (regKey != null && !regKey.trim().isEmpty()) {
                switch (logicForRegistration.specifyRoleKeyBelongsTo(request, regKey)) {
                    case ADMIN:
                        redirectIfSuccess(request, response, name, email, password, regKey, UserRole.ADMIN);
                        break;
                    case AUTHOR:
                        redirectIfSuccess(request, response, name, email, password, regKey, UserRole.AUTHOR);
                        break;
                    case null, default:
                        redirectIfError(request, response);
                        break;
                }
            } else {
                if (logicForRegistration.checkUserReg(name, email, password) != -1) {
                    request.getSession().setAttribute("regSuccess", name + ", поздравляем Вас с завершением регистрации в качестве пользователя!");
                    response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
                } else {
                    redirectToRegistrationPage(response);
                }
            }
        } catch (ServiceException e) {
            e.printStackTrace();
            request.getSession().setAttribute("regError", "Произошла ошибка при регистрации. Попробуйте позже.");
            redirectToRegistrationPage(response);
        }
    }

    private void redirectIfError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().setAttribute("invalidAuthorKey", "Вы ввели неверный ключ");
        redirectToRegistrationPage(response);
    }

    private void redirectIfSuccess(HttpServletRequest request, HttpServletResponse response, String name, String email, String password, String regKey, UserRole userRole) throws IOException, ServiceException {
        logicForRegistration.addInitialBioToExclusiveUser(logicForRegistration.checkExclusiveUserReg(name, email, password, regKey, userRole));
        request.getSession().setAttribute("regSuccess", name + ", поздравляем Вас с завершением регистрации, теперь Вы " + userRole.name());
        response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
    }

    private boolean detectErrorsInRegistrationData(HttpServletRequest request, HttpServletResponse response,
                                                   String email, String password, String confirmPassword) throws IOException, ServiceException {
        // Проводим валидацию введённого email для передачи информации по пути из формы к БД
        if (check.checkInvalidEmail(email)) {
            request.getSession().setAttribute("regError", "Неверный формат email");
            redirectToRegistrationPage(response);
            return true;
        }

        // Проверяем, что в базе данных ещё нет пользователя с таким email
        if (logicForRegistration.checkEmailExistsInDB(request, email)) {
            request.getSession().setAttribute("emailDuplicate", "Пользователь с таким e-mail уже существует");
            redirectToRegistrationPage(response);
            return true;
        }

        // Проверяем, что повторно введённый пароль верный
        if (!check.checkFieldsEquality(password, confirmPassword)) {
            request.getSession().setAttribute("regError", "Пароли не совпадают");
            redirectToRegistrationPage(response);
            return true;
        }

        return false;
    }

    private void redirectToRegistrationPage(HttpServletResponse response) throws IOException {
        response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
    }
}
