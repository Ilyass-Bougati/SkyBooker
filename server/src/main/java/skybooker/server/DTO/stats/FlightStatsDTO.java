package skybooker.server.DTO.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightStatsDTO {
    private Long volId;
    private String volIdentifier;
    private Long bookedCount;
    private Double totalCapacity;
    private Double revenue;
    private Double occupancyRate;
}
