package bg.tuvarna.ticketoffice.domain.models.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import bg.tuvarna.ticketoffice.domain.enums.Role;

public class UserProfileResponse {

    private Long id;

    private String name;

    private Role role;

    @JsonProperty("events_fee")
    private Double eventsFee;

    @JsonProperty("events_count")
    private Long eventsCount;

    @JsonProperty("active_events")
    private Long activeEvents;

    @JsonProperty("sold_events_tickets")
    private Long soldEventsTickets;

    @JsonProperty("average_rating")
    private Double averageRating;

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Double getEventsFee() {
        return eventsFee;
    }

    public void setEventsFee(Double eventsFee) {
        this.eventsFee = eventsFee;
    }

    public Long getEventsCount() {
        return eventsCount;
    }

    public void setEventsCount(Long eventsCount) {
        this.eventsCount = eventsCount;
    }

    public Long getActiveEvents() {
        return activeEvents;
    }

    public void setActiveEvents(Long activeEvents) {
        this.activeEvents = activeEvents;
    }

    public Long getSoldEventsTickets() {
        return soldEventsTickets;
    }

    public void setSoldEventsTickets(Long soldEventsTickets) {
        this.soldEventsTickets = soldEventsTickets;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
}
