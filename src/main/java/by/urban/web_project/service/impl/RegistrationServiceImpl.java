package by.urban.web_project.service.impl;

import by.urban.web_project.service.IRegistrationService;
import by.urban.web_project.service.ServiceException;
import jakarta.servlet.http.HttpServletRequest;

import static by.urban.web_project.mockdb.AuthorsDatabase.isEmailExistsInAuthorsDB;
import static by.urban.web_project.mockdb.UsersDatabase.isEmailExistsInUsersDB;

//Logic Stub For Registration
public class RegistrationServiceImpl implements IRegistrationService {
	public boolean checkReg(String name, String email, String password) throws ServiceException {
		return true;
	}

	public boolean checkIfEmailExistsInDB(HttpServletRequest request, String email) throws ServiceException{
		//пока не подключена БД
		return (isEmailExistsInAuthorsDB(email) || isEmailExistsInUsersDB(email));
	}
}
