package extclasses.final_project_spring.repository;

import extclasses.final_project_spring.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByActiveFalseAndBook_NameAndUser_Username(String bookName, String userName);

    Optional<Order> findByActiveTrueAndBook_NameAndUser_Username(String bookName, String userName);

    List<Order> findAllByActiveIsTrue();

    List<Order> findAllByActiveIsFalse();

    List<Order> findAllByActiveIsTrueAndUser_Username(String username);

    List<Order> findAllByActiveIsFalseAndUser_Username(String username);
}