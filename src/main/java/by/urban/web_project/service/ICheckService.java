package by.urban.web_project.service;

import by.urban.web_project.model.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ICheckService {
    boolean checkPassword(HttpServletRequest request, String passwordForRegistration, String confirmPassword) throws ServiceException;

    boolean checkInvalidEmail(String email) throws ServiceException;

    boolean checkIfRoleAuthorizedForAction(HttpServletRequest request, HttpServletResponse response, String sessionAttribute, UserRole requiredRole) throws ServiceException;
}
