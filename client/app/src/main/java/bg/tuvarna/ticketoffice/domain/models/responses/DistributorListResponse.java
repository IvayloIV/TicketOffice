package bg.tuvarna.ticketoffice.domain.models.responses;

public class DistributorListResponse {

    private Long id;

    private String name;

    private Double rating;

    private Long soldTickets;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getSoldTickets() {
        return soldTickets;
    }

    public void setSoldTickets(Long soldTickets) {
        this.soldTickets = soldTickets;
    }
}
