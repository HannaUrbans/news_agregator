package by.urban.web_project.service.impl;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.dao.DAOFactory;
import by.urban.web_project.dao.roles.IAuthorDAO;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;

public class ChangeProfileServiceImpl implements IChangeProfileService {

    private final IAuthorDAO authorDAO;

    //ранняя инициализация в конструкторе
    public ChangeProfileServiceImpl() throws ServiceException, DAOException {
        this.authorDAO = DAOFactory.getInstance().getAuthorDAO();
        if (this.authorDAO == null) {
            throw new ServiceException("AuthorDAO is not initialized");
        }
    }

    @Override
    public void updateBio(int authorId, String newBio) throws ServiceException {
        try {
            authorDAO.updateAuthorBio(authorId, newBio);
        } catch (DAOException e) {
            throw new ServiceException("Ошибка при обновлении био автора", e);
        }
    }


}
