package by.urban.web_project.service;

import by.urban.web_project.bean.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ICheckService {
    boolean checkFieldsEquality(String field1, String field2) throws ServiceException;

    boolean checkInvalidEmail(String email) throws ServiceException;

    boolean checkIfRoleAuthorizedForAction(HttpServletRequest request, HttpServletResponse response, String sessionAttribute, UserRole requiredRole) throws ServiceException;
}
