package by.urban.web_project.service.impl;

import by.urban.web_project.mockdb.AuthorRegistrationKeysDatabase;
import by.urban.web_project.service.ICheckService;
import jakarta.servlet.http.HttpServletRequest;

public class CheckServiceImpl implements ICheckService {

    public boolean checkPassword(HttpServletRequest request, String passwordForRegistration, String confirmPassword) {
        return (passwordForRegistration.equals(confirmPassword));
    }

    public boolean checkAuthorKey(HttpServletRequest request, String inputAuthorKey) {
        //пока не подключена БД
        return AuthorRegistrationKeysDatabase.isValidKey(inputAuthorKey);
    }
}