package by.urban.web_project.controller.concrete.impl;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import by.urban.web_project.controller.concrete.Command;
import by.urban.web_project.model.News;
import by.urban.web_project.service.LogicStubForAddingNewsToMainPage;
import by.urban.web_project.utils.SessionUtils;

public class GoToIndexPage implements Command {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		News breakingNews = LogicStubForAddingNewsToMainPage.getBreakingNews();
		News topNews = LogicStubForAddingNewsToMainPage.getTopNews();
		List<News> regularNewsList = LogicStubForAddingNewsToMainPage.getRegularNews();

		if (breakingNews != null) {
			request.setAttribute("breakingNews", breakingNews); // выводим последнюю добавленную срочную новость
		}
		if (topNews != null) {
			request.setAttribute("topNews", topNews); // выводим последнюю добавленную топовую новость
		}

		request.setAttribute("regularNews", regularNewsList);

		SessionUtils.logCurrentVisitor(request);
		System.out.println("Текущий URL: " + request.getRequestURL().toString());
		System.out.println("Номер сессии при открытии главной страницы: " + request.getSession().getId());
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main_index.jsp");
		dispatcher.forward(request, response);
	}
}