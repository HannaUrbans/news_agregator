package by.urban.web_project.controller.filters;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.bean.User;
import by.urban.web_project.service.IAuthorizationService;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "rememberMeFilter", urlPatterns = "/*")

//делать фильтр "проверка на null в сессии" нет смысла, потому что часть страниц д.б. доступна без авторизации, проще вынести проверку в утилиты

public class RememberMeFilter implements Filter {
    private final ServiceFactory serviceFactoryFactory;
    private final IAuthorizationService authorizationLogic;
    private final IChangeProfileService changeProfileService;

    public RememberMeFilter() throws ServiceException {
        serviceFactoryFactory = ServiceFactory.getInstance(); // Инициализация фабрики
        authorizationLogic = serviceFactoryFactory.getAuthorizationService();
        changeProfileService = serviceFactoryFactory.getChangeProfileService();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        if (session != null) {
            Auth auth = (Auth) session.getAttribute("auth");
            if (auth == null) {
                Cookie[] cookies = httpRequest.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if ("rememberMe".equals(cookie.getName())) {
                            String token = cookie.getValue();
                            if (token != null && !token.isEmpty()) {
                                User userFromCookies = null;
                                try {
                                    userFromCookies = authorizationLogic.findUserByToken(token);
                                    System.out.println(userFromCookies.toString());
                                } catch (ServiceException e) {
                                    throw new RuntimeException(e);
                                }

                                if (userFromCookies != null) {
                                    Auth authFromCookies = new Auth(userFromCookies.getId(), userFromCookies.getName(), userFromCookies.getRole());
                                    System.out.println(authFromCookies);
                                    httpRequest.getSession().setAttribute("auth", authFromCookies);
                                    httpRequest.getSession().setAttribute("id", authFromCookies.getId());
                                    httpRequest.getSession().setAttribute("role", authFromCookies.getRole().name().toLowerCase());
                                    httpRequest.getSession().setAttribute("name", authFromCookies.getName());
                                    String bio = null;
                                    try {
                                        bio = changeProfileService.getFieldData(authFromCookies.getId(), ProfileDataField.BIO);
                                    } catch (ServiceException e) {
                                        throw new RuntimeException(e);
                                    }
                                    session.setAttribute("bio", bio);
                                    break;
                                }
                            }
                        }
                    }
                }

            }
        }
        chain.doFilter(request, response);
    }


    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

