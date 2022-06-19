package bg.tuvarna.ticketoffice.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.holders.NotificationsViewHolder;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsViewHolder>{

    private final List<NotificationListResponse> data;

    public NotificationsAdapter(List<NotificationListResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_notification_element, parent, false);

        return new NotificationsViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {
        NotificationListResponse notification = data.get(position);
        holder.setMessage(String.format("%d. %s", position + 1, notification.getMessage()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
