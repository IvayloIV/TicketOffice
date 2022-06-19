package bg.tuvarna.ticketoffice.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.adapters.NotificationsAdapter;
import bg.tuvarna.ticketoffice.domain.models.responses.NotificationListResponse;

public class DialogNotificationsFragment extends DialogFragment {

    private static final String NOTIFICATIONS = "NOTIFICATIONS";


    public static DialogNotificationsFragment newInstance(List<NotificationListResponse> notifications) {
        DialogNotificationsFragment fragment = new DialogNotificationsFragment();
        Bundle args = new Bundle();
        args.putSerializable(NOTIFICATIONS, new ArrayList<>(notifications));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_notifications, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notificationsRv = view.findViewById(R.id.dialog_notifications_rv);
        Button btnOk = view.findViewById(R.id.notification_fragment_btn_ok);

        List<NotificationListResponse> notifications =
                (List<NotificationListResponse>) getArguments().getSerializable(NOTIFICATIONS);

        NotificationsAdapter adapter = new NotificationsAdapter(notifications);
        notificationsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationsRv.setAdapter(adapter);

        btnOk.setOnClickListener((v) -> dismiss());
    }
}
