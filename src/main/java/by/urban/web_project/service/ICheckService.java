package by.urban.web_project.service;

import jakarta.servlet.http.HttpServletRequest;

public interface ICheckService {
    boolean checkPassword(HttpServletRequest request, String passwordForRegistration, String confirmPassword);

    boolean checkAuthorKey(HttpServletRequest request, String inputAuthorKey);
}