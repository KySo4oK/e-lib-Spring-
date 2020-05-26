package extclasses.final_project_spring.service;

import extclasses.final_project_spring.dto.BookDTO;
import extclasses.final_project_spring.dto.OrderDTO;
import extclasses.final_project_spring.entity.Book;
import extclasses.final_project_spring.entity.Order;
import extclasses.final_project_spring.entity.Shelf;
import extclasses.final_project_spring.exception.BookNotAvailableException;
import extclasses.final_project_spring.exception.BookNotFoundException;
import extclasses.final_project_spring.exception.OrderNotFoundException;
import extclasses.final_project_spring.repository.BookRepository;
import extclasses.final_project_spring.repository.OrderRepository;
import extclasses.final_project_spring.repository.ShelfRepository;
import extclasses.final_project_spring.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Component
public class OrderService {
    private static final int PERIOD_OF_USE = 1;
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;
    private final ShelfRepository shelfRepository;
    private final UserRepository userRepository;

    public OrderService(OrderRepository orderRepository,
                        BookRepository bookRepository,
                        ShelfRepository shelfRepository,
                        UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.bookRepository = bookRepository;
        this.shelfRepository = shelfRepository;
        this.userRepository = userRepository;
    }

    public void createAndSaveNewOrder(BookDTO bookDTO, String username) {
        orderRepository.save(buildNewOrder(bookDTO, username));
    }

    private Order buildNewOrder(BookDTO bookDTO, String username) {
        return Order.builder()
                .user(userRepository.findByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not exist")))
                .book(bookRepository.findById(bookDTO.getId())
                        .orElseThrow(() -> new BookNotFoundException("book not exist")))
                .active(false)
                .endDate(LocalDate.now())
                .startDate(LocalDate.now())
                .build();
    }

    public void permitOrder(OrderDTO orderDTO) {
        log.info("permit order {}", orderDTO);
        orderRepository.save(activateAndChangeOrder(orderDTO));
    }

    private Order activateAndChangeOrder(OrderDTO orderDTO) {
        Order order = orderRepository
                .findById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException("Active order not exist"));
        if (!order.getBook().isAvailable()) throw new BookNotAvailableException("Book not available");
        order.setActive(true);
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(PERIOD_OF_USE));
        order.getBook().setAvailable(false);
        return order;
    }

    public List<OrderDTO> getActiveOrders() {
        return orderRepository
                .findAllByActiveIsTrue()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getPassiveOrders() {
        return orderRepository.findAllByActiveIsFalse()
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public List<OrderDTO> getActiveOrdersByUserName(String name) {
        log.info("active orders by username {}", name);
        return orderRepository.findAllByActiveIsTrueAndUser_Username(name)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    private OrderDTO buildOrderDTO(Order order) {
        return OrderDTO.builder()
                .bookName(getBookNameByLocale(order))
                .id(order.getOrderId())
                .userName(order.getUser().getUsername())
                .endDate(order.getEndDate()
                        .format(getFormatterByLocale()))
                .startDate(order.getStartDate()
                        .format(getFormatterByLocale()))
                .build();
    }

    private String getBookNameByLocale(Order order) {
        return LocaleContextHolder.getLocale().equals(Locale.ENGLISH) ?
                order.getBook().getName() : order.getBook().getNameUa();
    }

    private DateTimeFormatter getFormatterByLocale() {
        return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
                .withLocale(LocaleContextHolder.getLocale());
    }

    public List<OrderDTO> getPassiveOrdersByUserName(String name) {
        log.info("passive orders by username {}", name);
        return orderRepository.findAllByActiveIsFalseAndUser_Username(name)
                .stream()
                .map(this::buildOrderDTO)
                .collect(Collectors.toList());
    }

    public void returnBook(OrderDTO orderDTO) {
        log.info("return book {}", orderDTO.getBookName());
        saveBookAndDeleteOrder(getOrderAndPrepareBookForReturning(orderDTO));
    }

    @Transactional
    void saveBookAndDeleteOrder(Order order) {
        bookRepository.save(order.getBook());
        orderRepository.delete(order);
    }

    private Order getOrderAndPrepareBookForReturning(OrderDTO orderDTO) {
        Order order = orderRepository
                .findById(orderDTO.getId())
                .orElseThrow(() -> new OrderNotFoundException("order not exist"));
        prepareBookForReturning(order.getBook());
        return order;
    }

    private void prepareBookForReturning(Book book) {
        book.setAvailable(true);
        book.setShelf(shelfRepository.findByBookIsNull().orElse(new Shelf()));
    }
}
