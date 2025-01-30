package by.urban.web_project.service;

import by.urban.web_project.model.Auth;
import by.urban.web_project.model.Token;
import by.urban.web_project.model.User;

public interface IAuthorizationService {
    Auth checkAuth(String email, String password) throws ServiceException;

    String generateToken();

    Token saveToken(int id, String token) throws ServiceException;

    Token returnTokenIfPresent(int userId) throws ServiceException;

    boolean deleteToken(int id) throws ServiceException;

    User findUserByToken(String token) throws ServiceException;
}

