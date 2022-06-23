package bg.tuvarna.ticketoffice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.adapters.NotificationsAdapter;
import bg.tuvarna.ticketoffice.domain.models.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.fragments.listeners.ChangeDistributorsDialogListener;
import bg.tuvarna.ticketoffice.fragments.listeners.EventFilterDialogListener;

public class DialogEventsFragment extends DialogFragment {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final String START_DATE = "START_DATE";
    private static final String LOCATION = "LOCATION";
    private static final String ACTIVE = "ACTIVE";

    private EventFilterDialogListener listener;

    public static DialogEventsFragment newInstance(LocalDateTime startDate, String location, Boolean active) {
        DialogEventsFragment fragment = new DialogEventsFragment();
        Bundle args = new Bundle();
        if (startDate != null) {
            args.putSerializable(START_DATE, startDate.format(DATE_FORMATTER));
        } else {
            args.putSerializable(START_DATE, null);
        }
        args.putSerializable(LOCATION, location);
        args.putSerializable(ACTIVE, active);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnSearch = view.findViewById(R.id.events_fragment_btn_search);
        EditText startDateEt = view.findViewById(R.id.events_fragment_start_date_et);
        EditText locationEt = view.findViewById(R.id.events_fragment_location_et);
        CheckBox activeCb = view.findViewById(R.id.dialog_events_active_cb);

        String startDate = (String) getArguments().getSerializable(START_DATE);
        String location = (String) getArguments().getSerializable(LOCATION);
        Boolean active = (Boolean) getArguments().getSerializable(ACTIVE);

        if (startDate != null) {
            startDateEt.setText(startDate);
        }
        if (location != null) {
            locationEt.setText(location);
        }
        if (active != null) {
            activeCb.setChecked(active);
        }

        btnSearch.setOnClickListener(v -> {
            String startDateStr = startDateEt.getText().toString();
            if (startDateStr.length() > 0) {
                try {
                    LocalDateTime startDateValue = LocalDateTime.parse(startDateStr, DATE_FORMATTER);
                    listener.setStartDate(startDateValue);
                } catch (DateTimeParseException ex) {
                    Toast.makeText(getActivity(), R.string.events_fragment_start_date_error, Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                listener.setStartDate(null);
            }

            listener.setActive(activeCb.isChecked());

            String locationText = locationEt.getText().toString();
            if (locationText.length() > 0) {
                listener.setLocation(locationText);
            } else {
                listener.setLocation(null);
            }

            listener.filterResult();

            dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (EventFilterDialogListener) context;
    }
}
