package bg.tuvarna.ticketoffice.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.requests.CreateEventRequest;
import bg.tuvarna.ticketoffice.domain.models.requests.EditEventRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.EventDetailsResponse;
import bg.tuvarna.ticketoffice.fragments.DialogDistributorsFragment;
import bg.tuvarna.ticketoffice.fragments.listeners.ChangeDistributorsDialogListener;
import bg.tuvarna.ticketoffice.services.DistributorService;
import bg.tuvarna.ticketoffice.services.EventService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyEventActivity extends BaseActivity implements ChangeDistributorsDialogListener {

    private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private Long eventId;
    private EditText etTypeInput;
    private TextView tvTypeError;
    private EditText etPlacesCountInput;
    private TextView tvPlacesCountError;
    private EditText etPlacesTypeInput;
    private TextView tvPlacesTypeError;
    private EditText etPriceInput;
    private TextView tvPriceError;
    private EditText etTicketsPerUserInput;
    private TextView tvTicketsPerUserError;
    private EditText etStartDateInput;
    private TextView tvStartDateError;
    private EditText etLocationInput;
    private TextView tvLocationError;
    private List<Long> distributorIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_event);

        distributorIds = new ArrayList<>();
        eventId = getIntent().getLongExtra("eventId", -1);

        etTypeInput = findViewById(R.id.modify_event_et_type);
        tvTypeError = findViewById(R.id.tv_type_error);
        etPlacesCountInput = findViewById(R.id.modify_event_et_places_count);
        tvPlacesCountError = findViewById(R.id.tv_places_count_error);
        etPlacesTypeInput = findViewById(R.id.modify_event_et_places_type);
        tvPlacesTypeError = findViewById(R.id.tv_places_type_error);
        etPriceInput = findViewById(R.id.modify_event_et_price);
        tvPriceError = findViewById(R.id.tv_price_error);
        etTicketsPerUserInput = findViewById(R.id.modify_event_et_tickets_per_user);
        tvTicketsPerUserError = findViewById(R.id.tv_tickets_per_user_error);
        etStartDateInput = findViewById(R.id.modify_event_et_start_date);
        tvStartDateError = findViewById(R.id.tv_start_date_error);
        etLocationInput = findViewById(R.id.modify_event_et_location);
        tvLocationError = findViewById(R.id.tv_location_error);

        Button modifyEventButton = findViewById(R.id.modify_event_btn);
        TextView titleView = findViewById(R.id.modify_event_tv_title);
        if (eventId > -1) {
            findViewById(R.id.modify_event_btn_back).setOnClickListener(view -> showPage(EventListActivity.class));
            modifyEventButton.setText(R.string.modify_ticket_btn_edit);
            titleView.setText(R.string.modify_event_tv_edit_title);
        } else {
            findViewById(R.id.modify_event_btn_back).setOnClickListener(view -> showPage(HomeActivity.class));
            modifyEventButton.setText(R.string.modify_event_btn_create);
            titleView.setText(R.string.modify_event_tv_create_title);
        }

        modifyEventButton.setOnClickListener(view -> createEvent());
        findViewById(R.id.modify_event_add_distributors).setOnClickListener(view -> {
            DistributorService distributorService = getClient().getDistributorService();
            distributorService.list(new HashMap<>(), getClient().getAuthorizationHeader())
                .enqueue(new Callback<List<DistributorListResponse>>() {
                    @SuppressLint({"DefaultLocale", "SetTextI18n"})
                    @Override
                    public void onResponse(Call<List<DistributorListResponse>> call, Response<List<DistributorListResponse>> response) {
                        int code = response.code();

                        if (code == HttpURLConnection.HTTP_OK) {
                            List<DistributorListResponse> distributors = response.body();
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            DialogDistributorsFragment dialogDistributorsFragment = DialogDistributorsFragment.newInstance(distributors, distributorIds);
                            try {
                                dialogDistributorsFragment.show(fragmentManager, "distributors_fragment");
                            } catch (IllegalStateException ex) {
                                ex.printStackTrace();
                            }

                        } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            handleUnauthorized();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DistributorListResponse>> call, Throwable t) {
                        handleFailureRequest();
                    }
                });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (eventId > -1) {
            EventService eventService = getClient().getEventService();
            eventService.details(eventId, getClient().getAuthorizationHeader())
                .enqueue(new Callback<EventDetailsResponse>() {
                    @SuppressLint({"DefaultLocale", "SetTextI18n"})
                    @Override
                    public void onResponse(Call<EventDetailsResponse> call, Response<EventDetailsResponse> response) {
                        int code = response.code();

                        if (code == HttpURLConnection.HTTP_OK) {
                            EventDetailsResponse event = response.body();
                            etTypeInput.setText(event.getType());
                            etPlacesCountInput.setText(String.valueOf(event.getPlacesCount()));
                            if (event.getPlacesType() != null) {
                                etPlacesTypeInput.setText(event.getPlacesType());
                            }
                            etPriceInput.setText(String.valueOf(event.getPrice()));
                            if (event.getTicketsPerUser() != null) {
                                etTicketsPerUserInput.setText(String.valueOf(event.getTicketsPerUser()));
                            }
                            etStartDateInput.setText(event.getStartDate().format(DATE_FORMATTER));
                            etLocationInput.setText(event.getLocation());
                            distributorIds = event.getDistributorIds();
                        } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                            handleUnauthorized();
                        }
                    }

                    @Override
                    public void onFailure(Call<EventDetailsResponse> call, Throwable t) {
                        handleFailureRequest();
                    }
                });
        }
    }

    private void createEvent() {
        String type = etTypeInput.getText().toString();
        String placesCountStr = etPlacesCountInput.getText().toString();
        String placesType = etPlacesTypeInput.getText().toString();
        String priceStr = etPriceInput.getText().toString();
        String ticketsPerUserStr = etTicketsPerUserInput.getText().toString();
        String startDateStr = etStartDateInput.getText().toString();
        String location = etLocationInput.getText().toString();

        tvTypeError.setVisibility(View.INVISIBLE);
        tvPlacesCountError.setVisibility(View.INVISIBLE);
        tvPlacesTypeError.setVisibility(View.INVISIBLE);
        tvPriceError.setVisibility(View.INVISIBLE);
        tvTicketsPerUserError.setVisibility(View.INVISIBLE);
        tvStartDateError.setVisibility(View.INVISIBLE);
        tvLocationError.setVisibility(View.INVISIBLE);

        Integer placesCount = null;
        if (placesCountStr.length() != 0) {
            placesCount = Integer.parseInt(placesCountStr);
        }

        Double price = null;
        if (priceStr.length() != 0) {
            price = Double.parseDouble(priceStr);
        }

        Integer ticketsPerUser = null;
        if (ticketsPerUserStr.length() != 0) {
            ticketsPerUser = Integer.parseInt(ticketsPerUserStr);
        }

        LocalDateTime startDate = null;
        if (startDateStr.length() != 0) {
            try {
                startDate = LocalDateTime.parse(startDateStr, DATE_FORMATTER);
            } catch (DateTimeParseException ex) {
                tvStartDateError.setVisibility(View.VISIBLE);
                tvStartDateError.setText(R.string.modify_event_start_date_error);
                return;
            }
        }

        EventService eventService = getClient().getEventService();
        String authorizationHeader = getClient().getAuthorizationHeader();
        Call<CommonMessageResponse> eventCall;

        if (eventId > -1) {
            EditEventRequest editEventRequest = new EditEventRequest();
            editEventRequest.setId(eventId);
            editEventRequest.setType(type);
            editEventRequest.setPlacesCount(placesCount);
            editEventRequest.setPlacesType(placesType);
            editEventRequest.setPrice(price);
            editEventRequest.setTicketsPerUser(ticketsPerUser);
            editEventRequest.setStartDate(startDate);
            editEventRequest.setLocation(location);
            editEventRequest.setDistributorIds(distributorIds);

            eventCall = eventService.edit(editEventRequest, authorizationHeader);
        } else {
            CreateEventRequest createEventRequest = new CreateEventRequest();
            createEventRequest.setType(type);
            createEventRequest.setPlacesCount(placesCount);
            createEventRequest.setPlacesType(placesType);
            createEventRequest.setPrice(price);
            createEventRequest.setTicketsPerUser(ticketsPerUser);
            createEventRequest.setStartDate(startDate);
            createEventRequest.setLocation(location);
            createEventRequest.setDistributorIds(distributorIds);

            eventCall = eventService.create(createEventRequest, authorizationHeader);
        }

        eventCall.enqueue(new Callback<CommonMessageResponse>() {
            @Override
            public void onResponse(Call<CommonMessageResponse> call, Response<CommonMessageResponse> response) {
                int code = response.code();

                if (code == HttpURLConnection.HTTP_CREATED) {
                    CommonMessageResponse commonMessageResponse = response.body();
                    etTypeInput.setText("");
                    etPlacesCountInput.setText("");
                    etPlacesTypeInput.setText("");
                    etPriceInput.setText("");
                    etTicketsPerUserInput.setText("");
                    etStartDateInput.setText("");
                    etLocationInput.setText("");
                    distributorIds.clear();
                    Toast.makeText(ModifyEventActivity.this, commonMessageResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else if (code == HttpURLConnection.HTTP_OK) {
                    CommonMessageResponse commonMessageResponse = response.body();
                    Toast.makeText(ModifyEventActivity.this, commonMessageResponse.getMessage(), Toast.LENGTH_LONG).show();
                } else if (code == 422) {
                    showErrorInputMessages(response);
                } if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
                    handleCommonMessage(response);
                } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    handleUnauthorized();
                }
            }

            @Override
            public void onFailure(Call<CommonMessageResponse> call, Throwable t) {
                handleFailureRequest();
            }
        });
    }

    private void showErrorInputMessages(Response<CommonMessageResponse> response) {
        try {
            JSONObject jsonError = new JSONObject(response.errorBody().string());
            if (jsonError.has("type")) {
                tvTypeError.setText(jsonError.getString("type"));
                tvTypeError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("placesCount")) {
                tvPlacesCountError.setText(jsonError.getString("placesCount"));
                tvPlacesCountError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("placesType")) {
                tvPlacesTypeError.setText(jsonError.getString("placesType"));
                tvPlacesTypeError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("price")) {
                tvPriceError.setText(jsonError.getString("price"));
                tvPriceError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("ticketsPerUser")) {
                tvTicketsPerUserError.setText(jsonError.getString("ticketsPerUser"));
                tvTicketsPerUserError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("startDate")) {
                tvStartDateError.setText(jsonError.getString("startDate"));
                tvStartDateError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("location")) {
                tvLocationError.setText(jsonError.getString("location"));
                tvLocationError.setVisibility(View.VISIBLE);
            }
            if (jsonError.length() == 1 && jsonError.has("distributorIds")) {
                Toast.makeText(ModifyEventActivity.this, jsonError.getString("distributorIds"), Toast.LENGTH_LONG).show();
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFinishAddDialog(List<Long> distributorIds) {
        this.distributorIds = distributorIds;
    }
}