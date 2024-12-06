package by.urban.web_project.service.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.bean.User;
import by.urban.web_project.bean.UserRole;
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
     * Также будет использоваться в для изменения email и пароля (проверять совпадают ли старый и новый)
     */
    @Override
    public boolean checkFieldsEquality(String field1, String field2) throws ServiceException {
        return field1.equals(field2);
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