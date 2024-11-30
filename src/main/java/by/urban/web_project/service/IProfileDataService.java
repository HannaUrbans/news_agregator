package by.urban.web_project.service;

import by.urban.web_project.model.ProfileDataField;

public interface IProfileDataService {
     String getDataFromDatabase (int id, ProfileDataField profileDataField) throws ServiceException;
}
