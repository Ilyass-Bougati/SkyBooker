package skybooker.server.DTO.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaneOccupancyStatsDTO {
    private Long avionId;
    private Long totalPassengers;
    private Double totalCapacity;
    private Double occupancyRate;
    private String avionIdentifier;
}