package extclasses.final_project_spring.dto;

import lombok.*;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {
    private String name;
    private String[] tags;
    private String[] authors;
}