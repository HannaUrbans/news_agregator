package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.bean.Auth;
import by.urban.web_project.bean.ProfileDataField;
import by.urban.web_project.bean.UserRole;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.controller.utils.UpdateUtil;
import by.urban.web_project.controller.utils.UrlFormatterUtil;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import by.urban.web_project.utils.ProfileFieldToChange;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.urban.web_project.controller.utils.UrlFormatterUtil.formatRedirectUrl;

public class ChangeBio implements Command {

    private final IChangeProfileService changeProfileService;

    //может объединить с ChangeAccount?????????
    public ChangeBio() throws ServiceException {
        this.changeProfileService = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Auth auth = (Auth) request.getSession(false).getAttribute("auth");
        UserRole role = UserRole.valueOf(((String) request.getSession().getAttribute("role")).toUpperCase());
        // если не в сессии
        if (auth == null) {
            System.out.println("Пользователь не залогинен и пытается открыть страницу личного кабинета");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=GO_TO_AUTHENTIFICATION_PAGE");
            return;
        }

        // если от другой роли
        // надо в другом месте (страница с формой открывается при любой роли, но именно отправить не дает, надо шагом ранее эту проверку
        if (!UserRole.AUTHOR.equals(auth.getRole())) {
            System.out.println("Пользователь пытается войти в личный кабинет не своей роли");
            request.getSession().setAttribute("authError", "У Вас недостаточно прав для посещения этой страницы");
            response.sendRedirect("Controller?command=" + formatRedirectUrl(auth.getRole()));
            return;
        }

        int id = (int) request.getSession().getAttribute("id");

        // Получаем данные из формы
        String newBio = request.getParameter("newBio");

        boolean updated = false;

        if (newBio != null && !newBio.trim().isEmpty()) {
            boolean updateBio = UpdateUtil.updateProfileField(auth, newBio, ProfileFieldToChange.BIO, changeProfileService);
            if (updateBio) {
                updated = true;

            } else {
                request.getSession().setAttribute("changeBioError", "Произошла ошибка при обновлении биографии");
            }
        }
        // Проверка, были ли изменения
        if (updated) {
            try {
                String newBioFromDb = changeProfileService.getFieldData(id, ProfileDataField.BIO);
                request.getSession().setAttribute("updatedBio", newBioFromDb);

            } catch (ServiceException e) {
                e.printStackTrace();
            }
            request.getSession().setAttribute("changeBioSuccess", "Биография успешно обновлена!");
        } else {
            request.getSession().setAttribute("changeBioError", "Не было внесено изменений в биографию.");
        }

        // Перенаправление на страницу профиля
        response.sendRedirect("Controller?command=" + UrlFormatterUtil.formatRedirectUrl(role));
    }
}