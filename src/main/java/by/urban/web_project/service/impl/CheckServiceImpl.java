package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IAuthorRegistrationKeyDAO;
import by.urban.web_project.service.ICheckService;
import by.urban.web_project.service.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckServiceImpl implements ICheckService {

    private final IAuthorRegistrationKeyDAO checkKeyTool;

    //ранняя инициализация в конструкторе
    public CheckServiceImpl() throws DAOException {
        DAOFactory daoFactory = DAOFactory.getInstance();
        this.checkKeyTool = daoFactory.getAuthorRegistrationKeyDAO();
    }

    /**
     * Проверка, правильно ли повторно введен в форме регистрации пароль
     */
    @Override
    public boolean checkPassword(HttpServletRequest request, String passwordForRegistration, String confirmPassword) throws ServiceException {
        return passwordForRegistration.equals(confirmPassword);
    }

    /**
     * Сверяем, является ли введённый при регистрации ключ автора аналогичным вытянутому из БД ключу в слое ДАО
     */
    @Override
    public boolean checkAuthorKey(HttpServletRequest request, String inputAuthorKey) throws ServiceException {
        try {
            return checkKeyTool.isKeyValid(inputAuthorKey);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
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
}
