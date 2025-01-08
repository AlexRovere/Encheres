package fr.eni.encheres.services.utils;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ToolsService {

    public static String formatDate(LocalDate date) {
        if (date != null) {
            return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        }
        return "";
    }

    public static String formatDateToString(LocalDate date) {
        if (date != null) {
            return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }
        return "";
    }
}