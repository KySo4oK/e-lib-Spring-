package extclasses.final_project_spring.repository;

import extclasses.final_project_spring.entity.Shelf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    Optional<Shelf> findByBookIsNull();
}
