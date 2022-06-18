package bg.tuvarna.ticketoffice.domain.models.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RateUserRequest {

    @JsonProperty("user_id")
    private Long userId;

    private Integer rating;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
