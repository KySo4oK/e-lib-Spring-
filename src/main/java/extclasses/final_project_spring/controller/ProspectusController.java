package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.dto.FilterDTO;
import extclasses.final_project_spring.service.AuthorService;
import extclasses.final_project_spring.service.BookService;
import extclasses.final_project_spring.service.OrderService;
import extclasses.final_project_spring.service.TagService;
import extclasses.final_project_spring.util.CustomSuccessResponseEntity;
import extclasses.final_project_spring.util.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class ProspectusController {
    private final BookService bookService;
    private final TagService tagService;
    private final AuthorService authorService;
    private final OrderService orderService;
    private final CustomSuccessResponseEntity responseEntity;

    public ProspectusController(BookService bookService, TagService tagService, AuthorService authorService, OrderService orderService, CustomSuccessResponseEntity responseEntity) {
        this.bookService = bookService;
        this.tagService = tagService;
        this.authorService = authorService;
        this.orderService = orderService;
        this.responseEntity = responseEntity;
    }

    @GetMapping(value = "/books/{page}/{number}", produces = "application/json")
    public @ResponseBody
    List<BookDTO>
    getAvailableBooks(@PathVariable("page") String page, @PathVariable("number") String number) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(number));
        log.info("get books by pageable {}", pageable);
        return bookService.getAvailableBooks(pageable);
    }

    @GetMapping(value = "/tags", produces = "application/json")
    public @ResponseBody
    List<String>
    getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping(value = "/authors", produces = "application/json")
    public @ResponseBody
    List<String>
    getAllAuthors() {
        return authorService.getAllAuthors();
    }

    @PostMapping("/filter/{page}/{number}")
    public @ResponseBody
    List<BookDTO> getBooksByFilter(@PathVariable("page") String page,
                                   @PathVariable("number") String number,
                                   @RequestBody FilterDTO filterDTO) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page), Integer.parseInt(number));
        log.info("get books by pageable {}", pageable);
        log.info("get books by filter {}", filterDTO);
        Validator.checkFilterDTO(filterDTO);
        return bookService
                .getAvailableBookDTOsByFilter(filterDTO, pageable);
    }

    @PostMapping("/order")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, String>> orderBook(@RequestBody BookDTO bookDTO, Authentication authentication) {
        log.info("order book {}", bookDTO.getName());
        orderService.createAndSaveNewOrder(bookDTO, authentication.getName());
        return responseEntity.getEntityWithMessage("order.created");
    }
}
