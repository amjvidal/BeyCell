package src.main.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DataConverter {
    
    private static final DateTimeFormatter FORMATO_BR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // Converte LocalDate para String (ex: 2023-12-01 -> "01/12/2023")
    public static String dataParaString(LocalDate data) {
        if (data == null) return "";
        return data.format(FORMATO_BR);
    }

    // Converte String para LocalDate (ex: "01/12/2023" -> 2023-12-01)
    public static LocalDate stringParaData(String dataStr) {
        try {
            return LocalDate.parse(dataStr, FORMATO_BR);
        } catch (DateTimeParseException e) {
            System.err.println("Erro ao converter data: " + dataStr);
            return null;
        }
    }
    
    // Converte double para String de moeda (R$)
    public static String valorParaMoeda(double valor) {
        return String.format("R$ %.2f", valor);
    }
}