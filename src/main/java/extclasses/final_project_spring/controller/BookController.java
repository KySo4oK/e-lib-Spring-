package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.service.BookService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody BookDTO bookDTO) {
        log.info("add book {}", bookDTO.getName());
        bookService.saveNewBookFromClient(bookDTO);
    }

    @PutMapping("/edit")
    public void editBook(@RequestBody BookDTO bookDTO) {
        log.info("edit book {}", bookDTO.getName());
        bookService.editBookAndSave(bookDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        log.info("delete book with id {}", id);
        bookService.deleteBook(id);
    }
}
