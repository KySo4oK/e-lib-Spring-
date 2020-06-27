package extclasses.final_project_spring.service;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.dto.FilterDTO;
import extclasses.final_project_spring.entity.Author;
import extclasses.final_project_spring.entity.Book;
import extclasses.final_project_spring.entity.Shelf;
import extclasses.final_project_spring.entity.Tag;
import extclasses.final_project_spring.repository.BookRepository;
import extclasses.final_project_spring.repository.ShelfRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest {
    @Autowired
    private BookService bookService;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private ShelfRepository shelfRepository;
    @MockBean
    private TagService tagService;
    @MockBean
    private AuthorService authorService;
    private final Book book = Book.builder()
            .bookId(1L)
            .name("eng")
            .nameUa("ua")
            .authors(List.of())
            .tags(List.of())
            .available(true)
            .build();
    private final BookDTO bookDTO = BookDTO.builder()
            .name(book.getName())
            .id(book.getBookId())
            .tags(new String[]{})
            .authors(new String[]{})
            .build();

    @Test
    public void getAvailableBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Mockito.when(bookRepository.findAllByAvailableIsTrue(pageable))
                .thenReturn(List.of());
        Assert.assertTrue(bookService.getAvailableBooks(pageable).isEmpty());
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Mockito.when(bookRepository.findAllByAvailableIsTrue(pageable))
                .thenReturn(List.of(book));
        Assert.assertEquals(List.of(bookDTO), bookService.getAvailableBooks(pageable));
        LocaleContextHolder.setLocale(new Locale("ua"));
        bookDTO.setName(book.getNameUa());
        Assert.assertEquals(List.of(bookDTO), bookService.getAvailableBooks(pageable));
    }

    @Test
    public void getAvailableBooksByFilter() {
        Pageable pageable = PageRequest.of(0, 10);
        FilterDTO filterDTO = FilterDTO.builder()
                .name("")
                .authors(new String[]{})
                .tags(new String[]{})
                .build();
        Mockito.when(bookRepository.getAvailableBooksByFilter(
                filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(), pageable))
                .thenReturn(List.of());
        Assert.assertTrue(bookService.getAvailableBookDTOsByFilter(filterDTO, pageable).isEmpty());
        LocaleContextHolder.setLocale(Locale.ENGLISH);
        Mockito.when(bookRepository.getAvailableBooksByFilter(
                filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(), pageable))
                .thenReturn(List.of(book));
        Assert.assertEquals(List.of(bookDTO), bookService.getAvailableBookDTOsByFilter(filterDTO, pageable));
        LocaleContextHolder.setLocale(new Locale("ua"));
        bookDTO.setName(book.getNameUa());
        Mockito.when(bookRepository.getAvailableBooksByFilterUa(
                filterDTO.getName(),
                filterDTO.getAuthors(),
                filterDTO.getTags(), pageable))
                .thenReturn(List.of(book));
        Assert.assertEquals(List.of(bookDTO), bookService.getAvailableBookDTOsByFilter(filterDTO, pageable));
    }

    @Test
    public void saveNewBookFromClient() {
        Shelf shelf = new Shelf();
        Mockito.when(shelfRepository.findByBookIsNull())
                .thenReturn(Optional.of(shelf));
        bookDTO.setName(book.getName());
        bookDTO.setNameUa(book.getNameUa());
        bookService.saveNewBookFromClient(bookDTO);
        book.setBookId(null);
        Assert.assertEquals(book, shelf.getBook());
        Mockito.verify(bookRepository, Mockito.times(1))
                .save(ArgumentMatchers.eq(book));
        Mockito.verify(shelfRepository, Mockito.times(1))
                .save(ArgumentMatchers.eq(shelf));
    }

    @Test
    public void editBookAndSave() {
        bookDTO.setId(1L);
        book.setBookId(bookDTO.getId());
        Mockito.when(bookRepository.findById(bookDTO.getId()))
                .thenReturn(Optional.of(book));
        book.setAuthors(List.of(new Author()));
        book.setTags(List.of(new Tag()));
        Mockito.when(authorService.getAuthorsFromStringArray(bookDTO.getAuthors()))
                .thenReturn(book.getAuthors());
        Mockito.when(tagService.getTagsFromStringArray(bookDTO.getTags()))
                .thenReturn(book.getTags());
        bookService.saveEditedBook(bookDTO);
        Mockito.verify(bookRepository, Mockito.times(1))
                .save(ArgumentMatchers.eq(book));
    }

    @Test
    public void deleteBook() {
        long id = 10L;
        bookService.deleteBook(id);
        Mockito.verify(bookRepository, Mockito.times(1))
                .deleteById(id);
    }
}