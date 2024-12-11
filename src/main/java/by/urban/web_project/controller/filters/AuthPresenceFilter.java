package by.urban.web_project.controller.filters;

import by.urban.web_project.bean.Auth;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

//public class AuthPresenceFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
//
//        if (auth == null) {
//            System.out.println("Пользователь не залогинен и пытается открыть страницу добавления новостей");
//            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
//            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
//            return;
//        }
//
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
