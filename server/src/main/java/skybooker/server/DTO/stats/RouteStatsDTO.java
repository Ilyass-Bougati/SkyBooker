package skybooker.server.DTO.stats;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RouteStatsDTO {
    private String departureAirportCode;
    private String arrivalAirportCode;
    private Long passengerCount;
}
