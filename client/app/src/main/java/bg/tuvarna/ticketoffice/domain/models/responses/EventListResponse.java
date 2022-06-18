package bg.tuvarna.ticketoffice.domain.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EventListResponse {

    private Long id;

    private String type;

    @JsonProperty("tickets_per_user")
    private Integer ticketsPerUser;

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    private String location;

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
}
