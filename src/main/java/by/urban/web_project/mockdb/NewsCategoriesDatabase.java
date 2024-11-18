package by.urban.web_project.mockdb;

import by.urban.web_project.model.NewsCategory;

import java.util.HashMap;
import java.util.Map;

public class NewsCategoriesDatabase {
	// делаем имитацию БД с категориями новостей, где ключ - порядковый номер
	private static Map<Integer, NewsCategory> newsCategoriesDatabase = new HashMap<>();
	private static int id = 1;

	static {
		addNewsCategory(new NewsCategory(1, "3D печать", "Новости и инновации в области 3D печати, применения технологий для создания объектов."));
		addNewsCategory(new NewsCategory(2, "3D моделирование", "Все о 3D моделировании: от разработки моделей до их подготовки для печати."));
		addNewsCategory(new NewsCategory(3, "Литьё фотополимером", "Информация о технологиях и оборудовании для литья объектов с использованием фотополимерных материалов."));
		addNewsCategory(new NewsCategory(4, "Экология", "Как современные технологии 3D печати и моделирования могут повлиять на охрану окружающей среды."));
		addNewsCategory(new NewsCategory(5, "Косплей", "Категория посвящена изготовлению костюмов и аксессуаров для косплееров с помощью 3D печати."));
		addNewsCategory(new NewsCategory(6, "Материалы для печати", "Обзор различных материалов, используемых в 3D печати: от пластика до металлов и композитов."));
    }

	//эти методы временные, когда удалится заглушка и подключится БД, то они будут скорее всего в NewsCategoryDAOImpl
	private static void addNewsCategory(NewsCategory newsCategory) {
		newsCategoriesDatabase.put(id++, newsCategory);
	}

	public static NewsCategory getCategoryById(int newsCategoryId) {
		return newsCategoriesDatabase.get(newsCategoryId);
	}

	public static Map<Integer, NewsCategory> getAllCategories() {
		return newsCategoriesDatabase;
	}

}
