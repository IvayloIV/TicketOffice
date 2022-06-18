package bg.tuvarna.ticketoffice.domain.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class EventDetailsResponse {

    private Long id;

    private String type;

    @JsonProperty("places_count")
    private Integer placesCount;

    @JsonProperty("places_type")
    private String placesType;

    private Double price;

    @JsonProperty("tickets_per_user")
    private Integer ticketsPerUser;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    private String location;

    @JsonProperty("distributor_ids")
    private List<Long> distributorIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPlacesCount() {
        return placesCount;
    }

    public void setPlacesCount(Integer placesCount) {
        this.placesCount = placesCount;
    }

    public String getPlacesType() {
        return placesType;
    }

    public void setPlacesType(String placesType) {
        this.placesType = placesType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTicketsPerUser() {
        return ticketsPerUser;
    }

    public void setTicketsPerUser(Integer ticketsPerUser) {
        this.ticketsPerUser = ticketsPerUser;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Long> getDistributorIds() {
        return distributorIds;
    }

    public void setDistributorIds(List<Long> distributorIds) {
        this.distributorIds = distributorIds;
    }
}
