package by.urban.web_project.service;

import by.urban.web_project.service.impl.AuthorizationServiceImpl;
import by.urban.web_project.service.impl.CheckServiceImpl;
import by.urban.web_project.service.impl.NewsServiceImpl;
import by.urban.web_project.service.impl.RegistrationServiceImpl;

public final class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final ICheckService checkService = new CheckServiceImpl();
    private final INewsService newsService = new NewsServiceImpl();
    private final IAuthorizationService authorizationService = new AuthorizationServiceImpl();
    private final IRegistrationService registrationService = new RegistrationServiceImpl();

    private ServiceFactory() {}

    public static ServiceFactory getInstance() {
        return instance;
    }

    public ICheckService getCheckService() {
        return checkService;
    }

    public INewsService getNewsService() {
        return newsService;
    }

    public IAuthorizationService getAuthorizationService() {
        return authorizationService;
    }

    public IRegistrationService getRegistrationService() {
        return registrationService;
    }
}
