//package by.urban.web_project.controller.filters;
//
//import by.urban.web_project.bean.Auth;
//import by.urban.web_project.bean.UserRole;
//import jakarta.servlet.*;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.util.Map;
//
//@WebFilter(filterName = "rememberMeFilter", urlPatterns = "/*")
//// что вынесла: делать фильтр "проверка на null в сессии" отдельно от фильтра rememberMe нет смысла, потому что порядок фильтров не определен
//// в принципе делать фильтр "проверка на null в сессии" нет смысла, потому что часть страниц д.б. доступна без авторизации, проще вынести проверку в утилиты
//public class RememberMeFilter implements Filter {
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//
//        Auth auth = (Auth) httpRequest.getSession(false).getAttribute("auth");
//        if (auth == null) {
//            Cookie[] cookies = httpRequest.getCookies();
//            if (cookies != null) {
//                for (Cookie cookie : cookies) {
//                    if ("rememberMe".equals(cookie.getName())) {
//                        String token = cookie.getValue();
//                        //куки закрепляется за id пользователя в бд таблице кукис при АВТОРИЗАЦИИ если нажата галочка "запомни меня" -> пересмотреть контроллер авторизации
//                        //в дао дописать метод int findUserInDbByToken
//                        //в сервис дописать метод int findUserByToken берет инфу из бд
//                        Integer authIdFromCookies = сервисТул.findUserByToken(token);
//                        if (authIdFromCookies != null) {
//                            //дописать в сервис и дао метод "найтиРольПоАйди"
//                            UserRole role = сервисТул.найтиРольПоАйди(authIdFromCookies);
//                           switch (role){
//                               case UserRole.ADMIN:
//                                   ...
//                                   break;
//                               case UserRole.AUTHOR:
//                                   ...
//                                   break;
//                               case UserRole.USER:
//                                   ...
//                                   break;
//                           }
//                           //как из зарегистрированного сделать обобщенное auth или передавать конкретную роль?
//                            httpRequest.getSession().setAttribute("auth", authFromCookies);
//                            break;
//
//                        }
//                    }
//                }
//            }
//        }
//        chain.doFilter(request, response);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
