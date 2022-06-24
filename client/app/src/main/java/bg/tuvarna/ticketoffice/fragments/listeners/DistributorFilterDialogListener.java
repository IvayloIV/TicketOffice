package bg.tuvarna.ticketoffice.fragments.listeners;

public interface DistributorFilterDialogListener {

    void setName(String name);

    void setRating(Double rating);

    void setSoldTickets(Long soldTickets);

    void filterResult();
}
