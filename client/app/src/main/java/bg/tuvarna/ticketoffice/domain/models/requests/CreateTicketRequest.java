package bg.tuvarna.ticketoffice.domain.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateTicketRequest {

    @JsonProperty("customer_ucn")
    private String customerUcn;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("tickets_count")
    private Integer ticketsCount;

    @JsonProperty("event_id")
    private Long eventId;

    public String getCustomerUcn() {
        return customerUcn;
    }

    public void setCustomerUcn(String customerUcn) {
        this.customerUcn = customerUcn;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getTicketsCount() {
        return ticketsCount;
    }

    public void setTicketsCount(Integer ticketsCount) {
        this.ticketsCount = ticketsCount;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
