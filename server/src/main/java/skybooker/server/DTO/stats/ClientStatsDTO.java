package skybooker.server.DTO.stats;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientStatsDTO {
    private Long clientId;
    private String clientEmail;
    private Long reservationCount;
    private Double totalSpent;
    private Long passengerCount;
}