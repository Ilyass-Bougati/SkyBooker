package skybooker.server.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import skybooker.server.entity.Search;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchDTO {
    private Long id;

    @NotNull
    private Long villeDepartId;

    @NotNull
    private Long villeArriveeId;

    public SearchDTO(Search save) {
        setId(save.getId());
        setVilleDepartId(save.getVilleDepart().getId());
        setVilleArriveeId(save.getVilleArrivee().getId());
    }
}
