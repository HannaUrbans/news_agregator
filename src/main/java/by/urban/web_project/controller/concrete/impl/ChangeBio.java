package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeBio implements Command {

    private final IChangeProfileService updateTool;

    public ChangeBio() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        //кастуем, потому что getAttribute возвращает Object
        Author author = (Author) request.getSession().getAttribute("author");

        String newBio = request.getParameter("newBio");

        if (newBio != null && !newBio.trim().isEmpty()) {
            //меняем био
            try {
                //update запрос в sql с новым био
                updateTool.updateBio(author.getId(), newBio);
                //в бд уже новая информация, теперь объекту присваиваем новое био, чтобы передать в атрибуты
                author.setBio(newBio);
                //обновляем профиль автора и передаем дальше в сессию уже с новой биографией
                request.getSession().setAttribute("author", author);

                request.getSession().setAttribute("changeBioSuccess", "Биография успешно обновлена");

            } catch (ServiceException e) {
                e.printStackTrace();
            }
        } else {
            request.getSession().setAttribute("changeBioError", "Вы не написали ничего в поле");
        }

        response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
    }
}
