package extclasses.final_project_spring.dto;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDTO {
    private Long id;
    private String name;
    private String nameUa;
    private String[] tags;
    private String[] authors;
}
