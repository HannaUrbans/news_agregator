package by.urban.web_project.service;


import jakarta.servlet.http.HttpServletRequest;

public interface IRegistrationService {
    boolean checkUserReg(String name, String email, String password) throws ServiceException;

    boolean checkAuthorReg(String name, String email, String password, String bio, String authorKey) throws ServiceException;

    boolean checkUserEmailExistsInDB(HttpServletRequest request, String email) throws ServiceException;

    boolean checkAuthorEmailExistsInDB(HttpServletRequest request, String email) throws ServiceException;
}