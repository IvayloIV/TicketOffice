package bg.tuvarna.ticketoffice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.adapters.DistributorsAdapter;
import bg.tuvarna.ticketoffice.adapters.NotificationsAdapter;
import bg.tuvarna.ticketoffice.domain.models.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.fragments.listeners.ChangeDistributorsDialogListener;

public class DialogDistributorsFragment extends DialogFragment {

    private static final String DISTRIBUTORS = "DISTRIBUTORS";
    private static final String SELECTED_DISTRIBUTORS = "SELECTED_DISTRIBUTORS";

    private ChangeDistributorsDialogListener listener;

    public static DialogDistributorsFragment newInstance(List<DistributorListResponse> distributors,
                                                         List<Long> selectedDistributors) {
        DialogDistributorsFragment fragment = new DialogDistributorsFragment();
        Bundle args = new Bundle();
        args.putSerializable(DISTRIBUTORS, new ArrayList<>(distributors));
        args.putSerializable(SELECTED_DISTRIBUTORS, new ArrayList<>(selectedDistributors));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_distributors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView notificationsRv = view.findViewById(R.id.dialog_distributors_rv);
        Button btnOk = view.findViewById(R.id.distributors_fragment_btn_ok);

        List<DistributorListResponse> distributors =
                (List<DistributorListResponse>) getArguments().getSerializable(DISTRIBUTORS);

        List<Long> selectedDistributors =
                (List<Long>) getArguments().getSerializable(SELECTED_DISTRIBUTORS);

        DistributorsAdapter adapter = new DistributorsAdapter(distributors, new HashSet<>(selectedDistributors));
        notificationsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        notificationsRv.setAdapter(adapter);

        btnOk.setOnClickListener((v) -> {
            Set<Long> selectedIds = adapter.getSelectedIds();
            if (selectedIds.size() == 0) {
                Toast.makeText(getActivity(), "Please, select at least one distributor!", Toast.LENGTH_LONG).show();
            } else {
                listener.onFinishAddDialog(new ArrayList<>(selectedIds));
            }

            dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (ChangeDistributorsDialogListener)context;
    }
}
