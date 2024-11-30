package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.model.User;
import by.urban.web_project.model.UserRole;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckServiceImpl implements ICheckService {

    private final IUserDAO checkKeyTool;

    //ранняя инициализация в конструкторе
    public CheckServiceImpl() throws ServiceException {
        try {
            DAOFactory daoFactory = DAOFactory.getInstance();
            this.checkKeyTool = daoFactory.getUserDAO();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    /**
     * Проверка, правильно ли повторно введен в форме регистрации пароль
     */
    @Override
    public boolean checkPassword(HttpServletRequest request, String passwordForRegistration, String confirmPassword) throws ServiceException {
        return passwordForRegistration.equals(confirmPassword);
    }

    /**
     * Валидация email на бэке
     */
    @Override
    public boolean checkInvalidEmail(String email) throws ServiceException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return !matcher.matches();
    }

    /**
     * Метод для проверки существования живой сесии и соответствия роли пользователя из сессии требованиям
     * @param request - объект запроса, используется для получения информации из сессии
     * @param response - объект ответа, используется для отправки редиректа на другую страницу
     * @param sessionAttribute - имя атрибута в сессии, где хранится объект пользователя
     * @param requiredRole - требуемая роль пользователя для выполнения действия
     * @return true, если пользователь авторизован и имеет требуемую роль
     */
    public boolean checkIfRoleAuthorizedForAction(HttpServletRequest request, HttpServletResponse response, String sessionAttribute, UserRole requiredRole) throws ServiceException {
        // Извлекаем пользователя из сессии
        User user = (User) request.getSession().getAttribute(sessionAttribute);

        try {
            if (user == null) {
                request.setAttribute("errorMessage", "Вы не авторизованы.");
                response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
                return false;
            }

            // Проверяем роль пользователя
            if (user.getUserRole() != requiredRole) {
                request.setAttribute("errorMessage", "У вас нет прав автора для выполнения данного действия.");
                response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
                return false;
            }
        } catch (IOException e) {
            throw new ServiceException(e);
        }
        return true;
    }
}
