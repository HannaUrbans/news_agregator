package by.urban.web_project.logic;

import by.urban.web_project.bean.News;
import by.urban.web_project.bean.NewsDatabase;
import by.urban.web_project.bean.NewsImportance;
import java.util.ArrayList;
import java.util.List;

public class LogicStubForAddingNewsToMainPage {
	public static News getBreakingNews() {
		// вытягиваем новости из класса-имитации базы данных
		List<News> news = NewsDatabase.getAllNews();
		for (News item : news) {
			if (item.getImportance() == NewsImportance.BREAKING) {
				return item; // вернуть первую найденную срочную новость
			}
		}
		return null;
	}

	public static News getTopNews() {
		List<News> news = NewsDatabase.getAllNews();
		for (News item : news) {
			if (item.getImportance() == NewsImportance.TOP) {
				return item; // вернуть первую найденную топовую новость
			}
		}
		return null;
	}

	public static List<News> getRegularNews() {
		List<News> news = NewsDatabase.getAllNews();
		List<News> regularNewsList = new ArrayList<>();
		for (News item : news) {
			if (item.getImportance() == NewsImportance.REGULAR) {
				regularNewsList.add(item);
			}
		}
		return regularNewsList; // в случае неудачи вернется пустой список, отдельно не надо прописывать return
								// null;
	}
}
