package bg.tuvarna.ticketoffice.domain.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class EventListFilterRequest {

    @JsonProperty("start_date")
    private LocalDateTime startDate;

    private Boolean active;

    private String location;

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
