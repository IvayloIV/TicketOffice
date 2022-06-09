package bg.tuvarna.ticketoffice.domain.dtos.responses;

import bg.tuvarna.ticketoffice.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
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

    public UserProfileResponse(Long id, String name, Role role, Double eventsFee,
                               Long eventsCount, Long activeEvents, Long soldEventsTickets) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.eventsFee = eventsFee;
        this.eventsCount = eventsCount;
        this.activeEvents = activeEvents;
        this.soldEventsTickets = soldEventsTickets;
    }
}
