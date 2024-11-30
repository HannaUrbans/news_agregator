package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.IUserDAO;
import by.urban.web_project.model.ProfileDataField;
import by.urban.web_project.service.IProfileDataService;
import by.urban.web_project.service.ServiceException;

import java.util.Map;

public class ProfileDataServiceImpl implements IProfileDataService {

    private final DAOFactory daoFactory;
    private final IUserDAO userAuthorizationLogic;

    //ранняя инициализация в конструкторе
    public ProfileDataServiceImpl() throws ServiceException {
        try {
            this.daoFactory = DAOFactory.getInstance();
            this.userAuthorizationLogic = daoFactory.getUserDAO();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String getDataFromDatabase(int id, ProfileDataField profileDataField) throws ServiceException {
        try {
            Map<String, String> fields = userAuthorizationLogic.getUserProfileById(id);
            String fieldKey = (profileDataField.name()).toLowerCase();
            System.out.println("fieldKey " + fieldKey);
            String fieldValue = fields.get(fieldKey);


            if (fieldValue == null) {
                throw new IllegalArgumentException("Такого поля не существует");
            }

            return fieldValue;
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        catch (IllegalArgumentException e) {
            throw new ServiceException(e.getMessage(), e);
        }
    }

}
