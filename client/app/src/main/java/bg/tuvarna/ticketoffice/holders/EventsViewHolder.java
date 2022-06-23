package bg.tuvarna.ticketoffice.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.Consumer;

import bg.tuvarna.ticketoffice.R;

public class EventsViewHolder extends RecyclerView.ViewHolder{

    private final TextView typeTv;
    private final TextView startDateTv;
    private final TextView locationTv;

    public EventsViewHolder(@NonNull View itemView) {
        super(itemView);
        typeTv = itemView.findViewById(R.id.event_type_tv);
        startDateTv = itemView.findViewById(R.id.event_start_date_tv);
        locationTv = itemView.findViewById(R.id.event_location_tv);
    }

    public void setType(String type) {
        typeTv.setText(type);
    }

    public void setStartDate(String startDate) {
        startDateTv.setText(startDate);
    }

    public void setLocation(String location) {
        locationTv.setText(location);
    }
}
