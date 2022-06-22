package bg.tuvarna.ticketoffice.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.holders.DistributorsViewHolder;
import bg.tuvarna.ticketoffice.holders.NotificationsViewHolder;

public class DistributorsAdapter extends RecyclerView.Adapter<DistributorsViewHolder>{

    private final List<DistributorListResponse> distributors;
    private final Set<Long> selectedDistributors;

    public DistributorsAdapter(List<DistributorListResponse> distributors, Set<Long> selectedDistributors) {
        this.distributors = distributors;
        this.selectedDistributors = selectedDistributors;
    }

    @NonNull
    @Override
    public DistributorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.dialog_distributor_element, parent, false);

        return new DistributorsViewHolder(view, this::changeCheckedIds);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull DistributorsViewHolder holder, int position) {
        DistributorListResponse distributor = distributors.get(position);
        holder.setId(distributor.getId());
        holder.setName(distributor.getName());
        holder.setCheckBoxState(selectedDistributors.contains(distributor.getId()));
        holder.itemView.setOnClickListener((v) -> holder.addDistributorId(true));
    }

    @Override
    public int getItemCount() {
        return distributors.size();
    }

    public Set<Long> getSelectedIds() {
        return selectedDistributors;
    }

    private void changeCheckedIds(Boolean isChecked, Long userId) {
        if (isChecked) {
            selectedDistributors.add(userId);
        } else {
            selectedDistributors.remove(userId);
        }
    }
}
