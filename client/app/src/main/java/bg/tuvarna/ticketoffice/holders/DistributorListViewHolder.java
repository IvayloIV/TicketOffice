package bg.tuvarna.ticketoffice.holders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import bg.tuvarna.ticketoffice.R;

public class DistributorListViewHolder extends RecyclerView.ViewHolder{

    private final TextView nameTv;
    private final TextView ratingTv;
    private final TextView soldTicketsTv;

    public DistributorListViewHolder(@NonNull View itemView) {
        super(itemView);
        nameTv = itemView.findViewById(R.id.distributor_list_name_tv);
        ratingTv = itemView.findViewById(R.id.distributor_list_rating_tv);
        soldTicketsTv = itemView.findViewById(R.id.distributor_list_sold_tickets_tv);
    }

    public void setName(String name) {
        nameTv.setText(name);
    }

    public void setRating(String rating) {
        ratingTv.setText(rating);
    }

    public void setSoldTickets(String soldTickets) {
        soldTicketsTv.setText(soldTickets);
    }
}
