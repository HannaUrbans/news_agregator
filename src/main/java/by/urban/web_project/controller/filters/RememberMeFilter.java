package by.urban.web_project.controller.filters;

import by.urban.web_project.bean.Auth;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebFilter(filterName = "rememberMeFilter", urlPatterns = "/*")
// что вынесла: делать фильтр "проверка на null в сессии" отдельно от фильтра rememberMe нет смысла, потому что порядок фильтров не определен
// в принципе делать фильтр "проверка на null в сессии" нет смысла, потому что часть страниц д.б. доступна без авторизации, проще вынести проверку в утилиты
public class RememberMeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        Auth auth = (Auth) httpRequest.getSession(false).getAttribute("auth");

        if (auth == null) {
            System.out.println("Пользователь не залогинен и пытается открыть страницу добавления новостей");
            httpRequest.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            httpResponse.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
