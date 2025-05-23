package skybooker.server.DTO.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightThresholdStatsDTO {
    private Long volId;
    private Long bookedCount;
    private Double totalCapacity;
    private Double occupancyRate;
    private String volIdentifier;
    private String route;
    private String date;
}