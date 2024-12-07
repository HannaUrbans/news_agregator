package by.urban.web_project.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    public static String saveImage(Part part, ServletContext context) throws IOException {
        // Указываем абсолютный путь к директории для сохранения изображений
        String imagesDirPath = "E:\\практика\\webapps\\homeWork05\\news_agregator\\src\\main\\webapp\\images";
        File imagesDir = new File(imagesDirPath);

        // Создаем директорию, если она не существует
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }

        // Получаем имя файла
        String fileName = part.getSubmittedFileName();
        // Удаляем путь из имени файла, оставляя только имя
        String baseFileName = new File(fileName).getName();
        File file = new File(imagesDir, baseFileName);

        // Сохраняем файл
        try (InputStream inputStream = part.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        return "images/" + baseFileName; // Возвращаем относительный путь для базы данных
    }
}
