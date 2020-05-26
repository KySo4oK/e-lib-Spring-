package extclasses.final_project_spring.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FilterDTO {
    private String name;
    private String[] tags;
    private String[] authors;
}
