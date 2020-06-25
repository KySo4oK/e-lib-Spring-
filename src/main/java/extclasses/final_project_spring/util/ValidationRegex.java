package extclasses.final_project_spring.util;

public interface ValidationRegex {
    String bookName = "^[a-zA-Z0-9._]{2,30}$";
    String bookNameUa = "^[а-щА-ЩЬьЮюЯяЇїІіЄєҐґ0-9._]{2,30}$";
}