package by.urban.web_project.service;

import jakarta.servlet.http.HttpServletRequest;

public interface IRegistrationService {
    boolean checkReg(String name, String email, String password);

    boolean checkIfEmailExistsInDB(HttpServletRequest request, String email);
}