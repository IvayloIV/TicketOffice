package bg.tuvarna.ticketoffice.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Consumer;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.responses.EventListResponse;
import bg.tuvarna.ticketoffice.holders.EventListViewHolder;

public class EventListAdapter extends RecyclerView.Adapter<EventListViewHolder>{

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private final List<EventListResponse> data;
    private final Consumer<Long> onEditClick;
    private final boolean isOrganiser;

    public EventListAdapter(List<EventListResponse> data, Consumer<Long> onEditClick, boolean isOrganiser) {
        this.data = data;
        this.onEditClick = onEditClick;
        this.isOrganiser = isOrganiser;
    }

    @NonNull
    @Override
    public EventListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_element, parent, false);

        return new EventListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventListViewHolder holder, int position) {
        EventListResponse event = data.get(position);

        String type = event.getType();
        if (type.length() > 15) {
            type = type.substring(0, 15) + "...";
        }
        holder.setType(type);
        holder.setLocation(event.getLocation());
        holder.setStartDate(event.getStartDate().format(DATE_FORMATTER));
        if (isOrganiser) {
            holder.itemView.setOnLongClickListener(v -> {
                onEditClick.accept(event.getId());
                return true;
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
