package by.urban.web_project.service;

import jakarta.servlet.http.HttpServletRequest;

import static by.urban.web_project.mockdb.AuthorsDatabase.isEmailExistsInAuthorsDB;
import static by.urban.web_project.mockdb.UsersDatabase.isEmailExistsInUsersDB;

public class LogicStubForRegistration {
	public boolean checkReg(String name, String email, String password) {
		return true;
	}

	public boolean checkIfEmailExistsInDB(HttpServletRequest request, String email) {
		//пока не подключена БД
		return (isEmailExistsInAuthorsDB(email) || isEmailExistsInUsersDB(email));
	}
}
