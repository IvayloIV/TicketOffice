package bg.tuvarna.ticketoffice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.adapters.EventsAdapter;
import bg.tuvarna.ticketoffice.domain.models.responses.EventListResponse;
import bg.tuvarna.ticketoffice.fragments.DialogEventsFragment;
import bg.tuvarna.ticketoffice.fragments.listeners.EventFilterDialogListener;
import bg.tuvarna.ticketoffice.services.EventService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListActivity extends BaseActivity implements EventFilterDialogListener {

    private RecyclerView eventsRv;
    private LocalDateTime startDate;
    private String location;
    private Boolean active;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        active = true;
        eventsRv = findViewById(R.id.dialog_events_rv);
        findViewById(R.id.event_list_btn_back).setOnClickListener(view -> showPage(HomeActivity.class));
        findViewById(R.id.event_list_btn_filters).setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogEventsFragment dialogEventsFragment = DialogEventsFragment.newInstance(startDate, location, active);
            try {
                dialogEventsFragment.show(fragmentManager, "events_fragment");
            } catch (IllegalStateException ex) {
                ex.printStackTrace();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        filterResult();
    }

    private void editRedirect(Long eventId) {
        Intent intent = new Intent(getApplicationContext(), ModifyEventActivity.class);
        intent.putExtra("eventId", eventId);
        startActivity(intent);
    }

    @Override
    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public void filterResult() {
        EventService eventService = getClient().getEventService();
        Consumer<Long> editRedirect = this::editRedirect;
        Context context = getApplicationContext();

        System.out.println(LocalDateTime.now().toString());

        Map<String, String> filters = new HashMap<>();
        if (location != null) {
            filters.put("location", location);
        }
        if (active != null && active) {
            filters.put("active", Boolean.TRUE.toString());
        }
        if (startDate != null) {
            filters.put("startDate", startDate.toString());
        }

        eventService.list(filters, getClient().getAuthorizationHeader())
            .enqueue(new Callback<List<EventListResponse>>() {
                @Override
                public void onResponse(Call<List<EventListResponse>> call, Response<List<EventListResponse>> response) {
                    int code = response.code();
                    if (code == HttpURLConnection.HTTP_OK) {
                        List<EventListResponse> events = response.body();

                        EventsAdapter adapter = new EventsAdapter(events, editRedirect);
                        eventsRv.setLayoutManager(new LinearLayoutManager(context));
                        eventsRv.setAdapter(adapter);
                    } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handleUnauthorized();
                    }
                }

                @Override
                public void onFailure(Call<List<EventListResponse>> call, Throwable t) {
                    handleFailureRequest();
                }
            });
    }
}