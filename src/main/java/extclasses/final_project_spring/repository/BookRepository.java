package extclasses.final_project_spring.repository;

import extclasses.final_project_spring.entity.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAvailableIsTrue(Pageable pageable);

    Optional<Book> findByName(String name);

    @Query(nativeQuery = true, value =
            "select * from book  \n" +
                    "where available = true\n" +
                    "  and book_id in\n" +
                    "      (select book_id\n" +
                    "       from book_author \n" +
                    "       where author_id in (select author_id\n" +
                    "                           from author \n" +
                    "                           where name in (:authors)))\n" +
                    "  and book_id in (select book_id\n" +
                    "                  from book_tag \n" +
                    "                  where tag_id in (select tag_id \n" +
                    "                                   from tag  \n" +
                    "                                   where name in (:tags)))\n" +
                    "and name like :name\n")
    List<Book> getBooksByFilter(@Param("name") String partOfName,
                                @Param("authors") String[] authors,
                                @Param("tags") String[] tags, Pageable pageable);

    @Query(nativeQuery = true, value =
            "select * from book  \n" +
                    "where available = true\n" +
                    "  and book_id in\n" +
                    "      (select book_id\n" +
                    "       from book_author \n" +
                    "       where author_id in (select author_id\n" +
                    "                           from author \n" +
                    "                           where name_ua in (:authors)))\n" +
                    "  and book_id in (select book_id\n" +
                    "                  from book_tag \n" +
                    "                  where tag_id in (select tag_id \n" +
                    "                                   from tag  \n" +
                    "                                   where name_ua in (:tags)))\n" +
                    "and name_ua like :name\n")
    List<Book> getBooksByFilterUa(@Param("name") String partOfName,
                                  @Param("authors") String[] authors,
                                  @Param("tags") String[] tags, Pageable pageable);

    Optional<Book> findByNameUa(String bookNameUa);
}
