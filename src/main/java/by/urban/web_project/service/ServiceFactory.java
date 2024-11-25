package by.urban.web_project.service;

import by.urban.web_project.dao.DAOException;
import by.urban.web_project.service.impl.*;

public final class ServiceFactory {

    //!!! здесь приходится выбрасывать DAO исключения, есть подозрение, что неверно организована работа слоев!!!
    private static ServiceFactory instance;

    // Сервисы, инициализируемые при первом доступе
    private ICheckService checkService;
    private INewsService newsService;
    private IAuthorizationService authorizationService;
    private IRegistrationService registrationService;
    private IChangeProfileService changeProfileService;

    // Статический блок для инициализации фабрики
    static {
        try {
            instance = new ServiceFactory();
        } catch (ServiceException | DAOException e) {
            System.err.println("Ошибка инициализации ServiceFactory: " + e.getMessage());
            e.printStackTrace();
            //в статическом блоке нельзя вызывать checked исключения
            throw new ExceptionInInitializerError(e);
        }
    }

    private ServiceFactory() throws ServiceException, DAOException {
    }

    // Метод для получения экземпляра фабрики
    public static ServiceFactory getInstance() {
        if (instance == null) {
            throw new IllegalStateException("ServiceFactory не был инициализирован.");
        }
        return instance;
    }

    // Ленивая инициализация сервисов
    public ICheckService getCheckService() throws DAOException {
        if (checkService == null) {
            checkService = new CheckServiceImpl();
        }
        return checkService;
    }

    public INewsService getNewsService() {
        if (newsService == null) {
            newsService = new NewsServiceImpl();
        }
        return newsService;
    }

    public IAuthorizationService getAuthorizationService() throws DAOException, ServiceException {
        if (authorizationService == null) {
            authorizationService = new AuthorizationServiceImpl();
        }
        return authorizationService;
    }

    public IRegistrationService getRegistrationService() throws DAOException {
        if (registrationService == null) {
            registrationService = new RegistrationServiceImpl();
        }
        return registrationService;
    }

    public IChangeProfileService getChangeProfileService() throws DAOException, ServiceException {
        if (changeProfileService == null) {
            changeProfileService = new ChangeProfileServiceImpl();
        }
        return changeProfileService;
    }
}
