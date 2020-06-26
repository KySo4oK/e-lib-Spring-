package extclasses.final_project_spring.util;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.dto.FilterDTO;
import extclasses.final_project_spring.exception.CustomException;

import java.util.regex.Pattern;

public class Validator {
    public static void checkNewBook(BookDTO bookDTO) {
        if (Pattern.matches(ValidationRegex.bookName, bookDTO.getName()) &&
                Pattern.matches(ValidationRegex.bookNameUa, bookDTO.getNameUa()) &&
                bookDTO.getAuthors().length > 0 &&
                bookDTO.getTags().length > 0) {
            return;
        }
        throw new CustomException("impossible.book.values");
    }

    public static void checkFilterDTO(FilterDTO filterDTO) {
        if (filterDTO.getName().length() <= 2 ||
                filterDTO.getAuthors().length == 0 ||
                filterDTO.getTags().length == 0) {
            throw new CustomException("impossible.filter.values");
        }
    }
}