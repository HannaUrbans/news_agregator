package by.urban.web_project.utils;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ImageUtils {
    // Абсолютный путь к директории для изображений на сервере (Tomcat)
    private static final String IMAGES_DIRECTORY = "C:/tools/apache-tomcat-10.1.30/webapps/news_agregator_war/images";

    public static String saveImage(Part inputFile) throws ServletException, IOException {
        // Разрешаем только определенные типы изображений
        Set<String> allowedMimeTypes = new HashSet<>();
        allowedMimeTypes.add("image/png");
        allowedMimeTypes.add("image/jpg");
        allowedMimeTypes.add("image/jpeg");

        String inputMimeType = inputFile.getContentType();
        if (!allowedMimeTypes.contains(inputMimeType)) {
            throw new ServletException("Разрешены только файлы форматов PNG, JPG и JPEG.");
        }

        // Получаем имя файла
        String fileName = inputFile.getSubmittedFileName();

        // Формируем полный путь для сохранения файла на сервере
        String filePath = IMAGES_DIRECTORY + "/" + fileName;
        System.out.println("Сохраняем изображение по пути: " + filePath);

        // Сохраняем файл в указанную директорию
        try (BufferedInputStream bis = new BufferedInputStream(inputFile.getInputStream());
             BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath))) {

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }

        } catch (IOException exc) {
            exc.printStackTrace();
            throw new ServletException("Ошибка при сохранении изображения.");
        }

        File file = new File(filePath);
        if (file.exists()) {
            System.out.println("File exists: " + filePath);
        } else {
            System.out.println("File does not exist: " + filePath);
        }

        // Возвращаем относительный путь для использования в HTML
        return fileName;
    }
}
