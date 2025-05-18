package skybooker.server.DTO.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClassStatsDTO {
    private String className;
    private Double totalRevenue;
}
