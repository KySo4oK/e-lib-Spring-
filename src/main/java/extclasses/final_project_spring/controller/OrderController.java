package extclasses.final_project_spring.controller;

import extclasses.final_project_spring.dto.OrderDTO;
import extclasses.final_project_spring.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PutMapping("/permit")
    public void permitOrder(@RequestBody OrderDTO orderDTO) {
        log.info("permit order {}", orderDTO);
        orderService.permitOrder(orderDTO);
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
    public void returnBook(@RequestBody OrderDTO orderDTO) {
        log.info("return book {}", orderDTO.getBookName());
        orderService.returnBook(orderDTO);
    }
}
