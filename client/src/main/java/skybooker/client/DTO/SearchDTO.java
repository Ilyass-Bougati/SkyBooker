package skybooker.client.DTO;

public class SearchDTO {
    private Long id;
    private Long villeDepartId;
    private Long villeArriveeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVilleDepartId() {
        return villeDepartId;
    }

    public void setVilleDepartId(Long villeDepartId) {
        this.villeDepartId = villeDepartId;
    }

    public Long getVilleArriveeId() {
        return villeArriveeId;
    }

    public void setVilleArriveeId(Long villeArriveeId) {
        this.villeArriveeId = villeArriveeId;
    }
}
