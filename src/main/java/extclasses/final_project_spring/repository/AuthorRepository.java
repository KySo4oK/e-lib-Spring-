package extclasses.final_project_spring.repository;

import extclasses.final_project_spring.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByName(String name);

    Optional<Author> findByNameUa(String nameUa);
}
