package by.urban.web_project.service;

import by.urban.web_project.bean.User;

public interface IAuthorizationService {
    User checkAuth(String email, String password) throws ServiceException;
}
