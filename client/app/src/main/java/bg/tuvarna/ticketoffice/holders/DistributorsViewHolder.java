package bg.tuvarna.ticketoffice.holders;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.function.BiConsumer;

import bg.tuvarna.ticketoffice.R;

public class DistributorsViewHolder extends RecyclerView.ViewHolder{

    private Long id;
    private final TextView nameTv;
    private final CheckBox distributorCb;
    private final BiConsumer<Boolean, Long> changeCheckedIds;

    public DistributorsViewHolder(@NonNull View itemView, BiConsumer<Boolean, Long> changeCheckedIds) {
        super(itemView);
        this.changeCheckedIds = changeCheckedIds;
        nameTv = itemView.findViewById(R.id.distributor_name_tv);
        distributorCb = itemView.findViewById(R.id.dialog_distibutor_cb);
        distributorCb.setOnClickListener((v) -> addDistributorId(false));
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        nameTv.setText(name);
    }

    public void setCheckBoxState(boolean state) {
        distributorCb.setChecked(state);
    }

    public void addDistributorId(boolean changeCb) {
        if (changeCb) {
            distributorCb.setChecked(!distributorCb.isChecked());
        }
        changeCheckedIds.accept(distributorCb.isChecked(), id);
    }
}
