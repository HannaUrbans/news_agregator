package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.roles.Author;
import by.urban.web_project.model.roles.User;
import by.urban.web_project.service.IChangeProfileService;
import by.urban.web_project.service.ServiceException;
import by.urban.web_project.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ChangeName implements Command {
    private final IChangeProfileService updateTool;

    public ChangeName() throws ServiceException {
        this.updateTool = ServiceFactory.getInstance().getChangeProfileService();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String newName = request.getParameter("newName");
        User user = null;
        Author author = null;

        if (newName != null && !newName.trim().isEmpty()) {
            user = (User) request.getSession().getAttribute("user");
            author = (Author) request.getSession().getAttribute("author");

            if (author != null) {
                try {
                    updateTool.updateAuthorName(author.getId(), newName);
                    author.setName(newName);
                    request.getSession().setAttribute("author", author);
                    request.getSession().setAttribute("changeNameSuccess", "Имя успешно обновлено");
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            } else if (user != null) {
                try {
                    updateTool.updateUserName(user.getId(), newName);
                    user.setName(newName);
                    request.getSession().setAttribute("user", user);
                    request.getSession().setAttribute("changeNameSuccess", "Имя успешно обновлено");
                } catch (ServiceException e) {
                    e.printStackTrace();
                }
            } else {
                request.getSession().setAttribute("changeNameError", "Вы не задали новое имя");
            }

            if (author != null) {
                response.sendRedirect("Controller?command=GO_TO_AUTHOR_ACCOUNT_PAGE");
            } else {
                response.sendRedirect("Controller?command=GO_TO_USER_ACCOUNT_PAGE");
            }
        }
    }
}
