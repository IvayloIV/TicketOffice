package bg.tuvarna.ticketoffice.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.BiConsumer;

import bg.tuvarna.ticketoffice.R;

public class NotificationsViewHolder extends RecyclerView.ViewHolder{

    private final TextView messageTv;

    public NotificationsViewHolder(@NonNull View itemView) {
        super(itemView);
        messageTv = itemView.findViewById(R.id.notification_message_tv);
    }

    public void setMessage(String message) {
        messageTv.setText(message);
    }
}
