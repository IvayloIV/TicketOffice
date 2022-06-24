package bg.tuvarna.ticketoffice.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.EventListResponse;
import bg.tuvarna.ticketoffice.holders.DistributorListViewHolder;
import bg.tuvarna.ticketoffice.holders.EventListViewHolder;

public class DistributorListAdapter extends RecyclerView.Adapter<DistributorListViewHolder>{

    private final List<DistributorListResponse> data;
    private final Consumer<Long> onUserDetailsClick;
    private Context context;

    public DistributorListAdapter(List<DistributorListResponse> data, Consumer<Long> onUserDetailsClick) {
        this.data = data;
        this.onUserDetailsClick = onUserDetailsClick;
    }

    @NonNull
    @Override
    public DistributorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.distributor_element, parent, false);

        return new DistributorListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistributorListViewHolder holder, int position) {
        DistributorListResponse distributor = data.get(position);

        holder.setName(distributor.getName());
        Double rating = distributor.getRating();
        if (rating != null) {
            String ratingMsg = context.getResources().getString(R.string.distributor_list_rating_tv);
            holder.setRating(ratingMsg + " " + formatDouble(rating));
        }
        String soldTicketsMsg = context.getResources().getString(R.string.distributor_list_sold_tickets_tv);
        holder.setSoldTickets(soldTicketsMsg + " " + distributor.getSoldTickets().toString());
        holder.itemView.setOnClickListener(v -> onUserDetailsClick.accept(distributor.getId()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @SuppressLint("DefaultLocale")
    public static String formatDouble(double d) {
        if(d == (long) d) {
            return String.format("%d", (long)d);
        } else {
            return String.format("%s",d);
        }
    }
}
