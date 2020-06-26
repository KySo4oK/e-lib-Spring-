package extclasses.final_project_spring.service;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.dto.FilterDTO;
import extclasses.final_project_spring.entity.Author;
import extclasses.final_project_spring.entity.Book;
import extclasses.final_project_spring.entity.Shelf;
import extclasses.final_project_spring.entity.Tag;
import extclasses.final_project_spring.exception.CustomException;
import extclasses.final_project_spring.repository.BookRepository;
import extclasses.final_project_spring.repository.ShelfRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This service-class consists all operations with books and smaller part of them(tags and authors).
 *
 * @author Rostyslav Pryimak
 */
@Log4j2
@Component
public class BookService {
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;
    private final TagService tagService;
    private final AuthorService authorService;

    /**
     * Common constructor for creating BookService with tag- and author- services
     * and repositories for book and shelf
     *
     * @param bookRepository  - repository, which will be used for connecting with database,
     *                        actually with table, that represent book entity
     * @param shelfRepository - repository, which represents shelf entity
     * @param authorService   - service, which is used for return list of authors from String array
     * @param tagService      - service, which is used for return list of tags from String array
     */
    public BookService(BookRepository bookRepository,
                       ShelfRepository shelfRepository,
                       TagService tagService,
                       AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.tagService = tagService;
        this.authorService = authorService;
    }

    /**
     * Method, which return available books for user request
     *
     * @param pageable - object, which is used for implementing pagination
     * @return - result of getting books from database
     */
    public List<BookDTO> getAvailableBooks(Pageable pageable) {
        return bookRepository.findAllByAvailableIsTrue(pageable)
                .stream()
                .map(this::buildBookDTO)
                .collect(Collectors.toList());
    }

    /**
     * Method, which 'map' book, which we retrieve from repository to DTO object
     *
     * @param book - entity, which will be converted
     * @return - DTO object of book
     */
    private BookDTO buildBookDTO(Book book) {
        return BookDTO.builder()
                .id(book.getBookId())
                .authors(getArrayOfAuthors(book))
                .tags(getArrayOfTags(book))
                .name(getBookNameByLocale(book))
                .build();
    }

    /**
     * Method, which return book name depending on user locale
     *
     * @param book - entity, that contains localized names
     * @return - return localized name of book
     */
    private String getBookNameByLocale(Book book) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ? book.getName() : book.getNameUa();
    }

    /**
     * Method, which 'map' book tags to String array, getting only localized name
     *
     * @param book - entity, that contains List of tags
     * @return - array of localized tags name of book tags
     */
    private String[] getArrayOfTags(Book book) {
        return book.getTags()
                .stream()
                .map(this::getTagsByLocale)
                .toArray(String[]::new);
    }

    /**
     * Method, which return tag name depending on user locale
     *
     * @param tag - entity, that contains localized names
     * @return - return localized name of tag
     */
    private String getTagsByLocale(Tag tag) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                tag.getName() : tag.getNameUa();
    }

    /**
     * Method, which 'map' book authors to String array, getting only localized name
     *
     * @param book - entity, that contains List of authors
     * @return - array of localized authors name of book authors
     */
    private String[] getArrayOfAuthors(Book book) {
        return book.getAuthors()
                .stream()
                .map(this::getAuthorsByLocale)
                .toArray(String[]::new);
    }

    /**
     * Method, which return author name depending on user locale
     *
     * @param author - entity, that contains localized names
     * @return - return localized name of author
     */
    private String getAuthorsByLocale(Author author) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                author.getName() : author.getNameUa();
    }

    /**
     * Method, which save new book, which was created in client by admin
     *
     * @param bookDTO - DTO object of created by admin book
     */
    @Transactional
    public void saveNewBookFromClient(BookDTO bookDTO) {
        log.info("create book {}", bookDTO);
        Shelf shelf = shelfRepository.findByBookIsNull().orElse(new Shelf());
        Book book = BuildBookFromClient(bookDTO, shelf);
        bookRepository.save(book);
        shelf.setBook(book);
        shelfRepository.save(shelf);
    }

    /**
     * Method, which create book entity from DTO object and shelf
     *
     * @param bookDTO - DTO object of created by admin book
     * @param shelf   - entity, which will be hold book
     * @return - created book entity
     */
    private Book BuildBookFromClient(BookDTO bookDTO, Shelf shelf) {
        return Book.builder()
                .name(bookDTO.getName())
                .nameUa(bookDTO.getNameUa())
                .shelf(shelf)
                .authors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()))
                .tags(tagService.getTagsByStringArray(bookDTO.getTags()))
                .available(true)
                .build();
    }

    /**
     * Method, which return DTO objects of available books for user request by filter
     *
     * @param filterDTO - DTO object, which is used for filtering books
     * @param pageable  - object, which is used for implementing pagination
     * @return - DTO result of getting books from database by filter
     */
    public List<BookDTO> getAvailableBooksByFilter(FilterDTO filterDTO, Pageable pageable) {
        return getBooksByFilter(filterDTO, pageable)
                .stream()
                .map(this::buildBookDTO)
                .collect(Collectors.toList());

    }

    /**
     * Method, which get books from database by filter and locale
     *
     * @param filterDTO - DTO object, which is used for filtering books
     * @param pageable  - object, which is used for implementing pagination
     * @return - result of getting books from database by filter and locale
     */
    private List<Book> getBooksByFilter(FilterDTO filterDTO, Pageable pageable) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                bookRepository.getBooksByFilter(
                        filterDTO.getName(),
                        filterDTO.getAuthors(),
                        filterDTO.getTags(), pageable) :
                bookRepository.getBooksByFilterUa(
                        filterDTO.getName(),
                        filterDTO.getAuthors(),
                        filterDTO.getTags(), pageable);
    }

    /**
     * Method, which implement editing book from client
     *
     * @param bookDTO - DTO object of edited book by admin
     */
    public void editBookAndSave(BookDTO bookDTO) {
        log.info("save book {}", bookDTO);
        bookRepository.save(getEditedBook(bookDTO));
    }

    /**
     * Method, which 'map' edited DTO object to entity
     *
     * @param bookDTO - DTO object of edited book by admin
     * @return - edited book entity
     */
    private Book getEditedBook(BookDTO bookDTO) {
        Book book = bookRepository
                .findById(bookDTO.getId())
                .orElseThrow(() -> new CustomException("book.not.found"));
        book.setAuthors(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()));
        book.setTags(tagService.getTagsByStringArray(bookDTO.getTags()));
        return book;
    }

    /**
     * Method, which delete book by admin request
     *
     * @param id - id of book, which will be deleted
     */
    public void deleteBook(long id) {
        log.info("delete book with id {}", id);
        bookRepository.deleteById(id);
    }
}