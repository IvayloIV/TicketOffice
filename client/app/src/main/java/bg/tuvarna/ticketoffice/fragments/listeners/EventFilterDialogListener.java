package bg.tuvarna.ticketoffice.fragments.listeners;

import java.time.LocalDateTime;

public interface EventFilterDialogListener {

    void setStartDate(LocalDateTime startDate);

    void setLocation(String location);

    void setActive(Boolean active);

    void filterResult();
}
