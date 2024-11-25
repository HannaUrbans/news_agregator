package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.dao.DAOException;
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

    public DoRegistration() throws DAOException {
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Параметры приходят из формы регистрации
            String nameForRegistration = request.getParameter("name");
            String emailForRegistration = request.getParameter("email");
            String authorKey = request.getParameter("authorKey");
            String passwordForRegistration = request.getParameter("password");
            String confirmPassword = request.getParameter("confirmPassword");
            // Прописывается в личном кабинете, а не при регистрации
            String bio = "";

            // Проводим валидацию введённого email для передачи информации в регистрацию
            if (check.checkInvalidEmail(emailForRegistration)) {
                throw new ServiceException("Неверный формат email");
            }

            // Проверяем, что в базе данных ещё нет пользователя с таким email
            if (logicForRegistration.checkUserEmailExistsInDB(request, emailForRegistration) || logicForRegistration.checkAuthorEmailExistsInDB(request, emailForRegistration)) {
                request.getSession().setAttribute("emailDuplicate", "Пользователь с таким e-mail уже существует");
                response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                return;
            }

            // Проверяем, что повторно введённый пароль верный
            if (!check.checkPassword(request, passwordForRegistration, confirmPassword)) {
                request.getSession().setAttribute("regError", "Пароли не совпадают");
                response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                return;
            }
            //String hashedPassword = PasswordHasher.hashPasswordForRegistration(passwordForRegistration);

            // Проверяем поле "ключ автора"
            if (authorKey != null && !authorKey.trim().isEmpty()) {

                //сверяем значение поля с бд ключей
                if (!check.checkAuthorKey(request, authorKey)) {
                    request.getSession().setAttribute("invalidAuthorKey", "Вы ввели неверный ключ автора");
                    response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                    return;
                }
                //if (logicForRegistration.checkAuthorReg(nameForRegistration, emailForRegistration, hashedPassword, bio, authorKey)) {

                //если ключ верный, то регистрируем как автора
                if (logicForRegistration.checkAuthorReg(nameForRegistration, emailForRegistration, passwordForRegistration, bio, authorKey)) {
                    request.getSession().setAttribute("regSuccess", nameForRegistration + ", поздравляем Вас с завершением регистрации как автора!");
                    response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
                } else {
                    //добавить атрибут, что регистрация автора не удалась?
                    response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                }
            } else {
                // if (logicForRegistration.checkUserReg(nameForRegistration, emailForRegistration, hashedPassword)) {

                // поле пустое, поэтому регистрируем как пользователя
                if (logicForRegistration.checkUserReg(nameForRegistration, emailForRegistration, passwordForRegistration)) {
                    request.getSession().setAttribute("regSuccess", nameForRegistration + ", поздравляем Вас с завершением регистрации!");
                    response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
                } else {
                    response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
                }
            }

        } catch (ServiceException e) {
            e.printStackTrace();
            request.getSession().setAttribute("regError", "Произошла ошибка при регистрации. Попробуйте позже.");
            response.sendRedirect("Controller?command=GO_TO_REGISTRATION_PAGE");
        }
    }
}
