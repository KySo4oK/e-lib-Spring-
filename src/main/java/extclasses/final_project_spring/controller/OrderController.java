package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.OrderDTO;
import extclasses.final_project_spring.service.OrderService;
import extclasses.final_project_spring.util.CustomSuccessResponseEntity;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class OrderController {
    private final OrderService orderService;
    private final CustomSuccessResponseEntity responseEntity;

    public OrderController(OrderService orderService, CustomSuccessResponseEntity responseEntity) {
        this.orderService = orderService;
        this.responseEntity = responseEntity;
    }

    @PutMapping("/permit")
    public ResponseEntity<Map<String, String>> permitOrder(@RequestBody OrderDTO orderDTO) {
        log.info("permit order {}", orderDTO);
        orderService.permitOrder(orderDTO);
        return responseEntity.getEntityWithMessage("order.permitted");
    }

    @GetMapping("/active")
    public @ResponseBody
    List<OrderDTO> getActiveOrders() {
        return orderService.getActiveOrders();
    }

    @GetMapping("/passive")
    public @ResponseBody
    List<OrderDTO> getPassiveOrders() {
        return orderService.getPassiveOrders();
    }

    @GetMapping("/user/active")
    public @ResponseBody
    List<OrderDTO> getActiveOrdersByUser(Authentication authentication) {
        return orderService.getActiveOrdersByUserName(authentication.getName());
    }

    @GetMapping("/user/passive")
    public @ResponseBody
    List<OrderDTO> getPassiveOrdersByUser(Authentication authentication) {
        return orderService.getPassiveOrdersByUserName(authentication.getName());
    }

    @PutMapping("/user/return")
    public ResponseEntity<Map<String, String>> returnBook(@RequestBody OrderDTO orderDTO) {
        log.info("return book {}", orderDTO.getBookName());
        orderService.returnBook(orderDTO);
        return responseEntity.getEntityWithMessage("book.returned");
    }
}
