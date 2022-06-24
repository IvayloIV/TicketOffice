package bg.tuvarna.ticketoffice.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.fragments.listeners.DistributorFilterDialogListener;

public class DialogDistributorListFragment extends DialogFragment {

    private static final String NAME = "NAME";
    private static final String RATING = "RATING";
    private static final String SOLD_TICKETS = "SOLD_TICKETS";

    private DistributorFilterDialogListener listener;

    public static DialogDistributorListFragment newInstance(String name, Double rating, Long soldTickets) {
        DialogDistributorListFragment fragment = new DialogDistributorListFragment();
        Bundle args = new Bundle();
        args.putSerializable(NAME, name);
        args.putSerializable(RATING, rating);
        args.putSerializable(SOLD_TICKETS, soldTickets);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog_distributor_filters, container, false);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnSearch = view.findViewById(R.id.events_fragment_btn_search);
        EditText nameEt = view.findViewById(R.id.distributor_filters_name_et);
        EditText ratingEt = view.findViewById(R.id.distributor_filters_rating_et);
        EditText soldTicketsEt = view.findViewById(R.id.distributor_filters_sold_tickets_et);

        String name = (String) getArguments().getSerializable(NAME);
        Double rating = (Double) getArguments().getSerializable(RATING);
        Long soldTickets = (Long) getArguments().getSerializable(SOLD_TICKETS);

        if (name != null) {
            nameEt.setText(name);
        }
        if (rating != null) {
            ratingEt.setText(formatDouble(rating));
        }
        if (soldTickets != null) {
            soldTicketsEt.setText(soldTickets.toString());
        }

        btnSearch.setOnClickListener(v -> {
            String nameText = nameEt.getText().toString();
            if (nameText.length() > 0) {
                listener.setName(nameText);
            } else {
                listener.setName(null);
            }

            String ratingText = ratingEt.getText().toString();
            if (ratingText.length() > 0) {
                listener.setRating(Double.parseDouble(ratingText));
            } else {
                listener.setRating(null);
            }

            String soldTicketsText = soldTicketsEt.getText().toString();
            if (soldTicketsText.length() > 0) {
                listener.setSoldTickets(Long.parseLong(soldTicketsText));
            } else {
                listener.setSoldTickets(null);
            }

            listener.filterResult();
            dismiss();
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (DistributorFilterDialogListener) context;
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
