package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.service.BookService;
import extclasses.final_project_spring.util.SuccessResponseEntity;
import extclasses.final_project_spring.util.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Log4j2
@RestController
public class BookController {
    private final BookService bookService;
    private final SuccessResponseEntity responseEntity;

    public BookController(BookService bookService, SuccessResponseEntity responseEntity) {
        this.bookService = bookService;
        this.responseEntity = responseEntity;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> addBook(@RequestBody BookDTO bookDTO) {
        log.info("add book {}", bookDTO.getName());
        Validator.checkNewBook(bookDTO);
        bookService.saveNewBookFromClient(bookDTO);
        return responseEntity.getSuccessResponseEntityWithMessage("book.added");
    }

    @PutMapping("/edit")
    public ResponseEntity<Map<String, String>> editBook(@RequestBody BookDTO bookDTO) {
        log.info("edit book {}", bookDTO.getName());
        bookService.saveEditedBook(bookDTO);
        return responseEntity.getSuccessResponseEntityWithMessage("book.edited");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, String>> deleteBook(@PathVariable("id") long id) {
        log.info("delete book with id {}", id);
        bookService.deleteBook(id);
        return responseEntity.getSuccessResponseEntityWithMessage("book.deleted");
    }
}
