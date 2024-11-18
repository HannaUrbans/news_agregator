package by.urban.web_project.controller.concrete.impl;

import by.urban.web_project.controller.concrete.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class GoToChangeNameForm implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //здесь диспатчер, потому что страница лежит в веб-инф + не передаем информацию через форму, можно не бояться f5
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/changeNameForm.jsp");
        dispatcher.forward(request, response);
    }
}
