package extclasses.final_project_spring.util;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.dto.UserDTO;
import extclasses.final_project_spring.exception.IncorrectDataException;

import java.util.regex.Pattern;

public class Validator {
    public static void checkRegistration(UserDTO userDTO) {
        if (Pattern.matches(ValidationRegex.usernameRegex, userDTO.getUsername())
                && Pattern.matches(ValidationRegex.passwordRegex, userDTO.getPassword())
                && Pattern.matches(ValidationRegex.phoneRegex, userDTO.getPhone())
                && Pattern.matches(ValidationRegex.emailRegex, userDTO.getEmail())) {
            return;
        }
        throw new IncorrectDataException("bad.credentials");
    }

    public static void checkNewBook(BookDTO bookDTO) {
        if (Pattern.matches(ValidationRegex.bookName, bookDTO.getName()) &&
                Pattern.matches(ValidationRegex.bookNameUa, bookDTO.getNameUa()) &&
                bookDTO.getAuthors().length > 0 &&
                bookDTO.getTags().length > 0) {
            return;
        }
        throw new IncorrectDataException("impossible.book.values");
    }
}