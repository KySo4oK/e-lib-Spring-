package extclasses.final_project_spring.dto;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private Long id;
    private String bookName;
    private String userName;
    private String startDate;
    private String endDate;
}
