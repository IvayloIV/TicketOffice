package bg.tuvarna.ticketoffice.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.requests.CreateTicketRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.EventListResponse;
import bg.tuvarna.ticketoffice.services.EventService;
import bg.tuvarna.ticketoffice.services.TicketService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateTicketActivity extends BaseActivity {

    private List<EventListResponse> events;

    private Spinner cb;
    private EditText etCustomerUcnInput;
    private TextView tvCustomerUcnError;
    private EditText etCustomerNameInput;
    private TextView tvCustomerNameError;
    private EditText etTicketsCountInput;
    private TextView tvTicketsCountError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ticket);

        cb = findViewById(R.id.create_ticket_sp_event);
        etCustomerUcnInput = findViewById(R.id.create_ticket_et_customer_ucn);
        tvCustomerUcnError = findViewById(R.id.tv_customer_ucn_error);
        etCustomerNameInput = findViewById(R.id.create_ticket_et_customer_name);
        tvCustomerNameError = findViewById(R.id.tv_customer_name_error);
        etTicketsCountInput = findViewById(R.id.create_ticket_et_tickets_count);
        tvTicketsCountError = findViewById(R.id.tv_tickets_count_error);

        findViewById(R.id.create_ticket_btn_back).setOnClickListener(view -> showPage(HomeActivity.class));
        findViewById(R.id.create_ticket_btn).setOnClickListener(view -> createTicket());
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventService eventService = getClient().getEventService();
        eventService.distributorEvents(getClient().getAuthorizationHeader())
            .enqueue(new Callback<List<EventListResponse>>() {
                @Override
                public void onResponse(Call<List<EventListResponse>> call, Response<List<EventListResponse>> response) {
                    int code = response.code();

                    if (code == HttpURLConnection.HTTP_OK) {
                        events = response.body();
                        String[] eventTypes = events.stream()
                                .map(EventListResponse::getType)
                                .toArray(String[]::new);

                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateTicketActivity.this,
                                android.R.layout.simple_spinner_item, eventTypes);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cb.setAdapter(adapter);
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

    private void createTicket() {
        String customerUcn = etCustomerUcnInput.getText().toString();
        String customerName = etCustomerNameInput.getText().toString();
        String ticketsCountStr = etTicketsCountInput.getText().toString();
        Integer ticketsCount = null;
        if (ticketsCountStr.length() != 0) {
            ticketsCount = Integer.parseInt(ticketsCountStr);
        }
        Long eventId = events.get(cb.getSelectedItemPosition()).getId();

        tvCustomerUcnError.setVisibility(View.INVISIBLE);
        tvCustomerNameError.setVisibility(View.INVISIBLE);
        tvTicketsCountError.setVisibility(View.INVISIBLE);

        CreateTicketRequest createTicketRequest = new CreateTicketRequest();
        createTicketRequest.setCustomerUcn(customerUcn);
        createTicketRequest.setCustomerName(customerName);
        createTicketRequest.setTicketsCount(ticketsCount);
        createTicketRequest.setEventId(eventId);

        TicketService ticketService = getClient().getTicketService();
        String authorizationHeader = getClient().getAuthorizationHeader();
        Call<CommonMessageResponse> ticketCall = ticketService.create(createTicketRequest, authorizationHeader);
        ticketCall.enqueue(new Callback<CommonMessageResponse>() {
            @Override
            public void onResponse(Call<CommonMessageResponse> call, Response<CommonMessageResponse> response) {
                int code = response.code();

                if (code == HttpURLConnection.HTTP_CREATED) {
                    CommonMessageResponse commonMessageResponse = response.body();
                    etCustomerUcnInput.setText("");
                    etCustomerNameInput.setText("");
                    etTicketsCountInput.setText("");
                    cb.setSelection(0);
                    Toast.makeText(CreateTicketActivity.this, commonMessageResponse.getMessage(), Toast.LENGTH_LONG).show();
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
            if (jsonError.has("customerUcn")) {
                tvCustomerUcnError.setText(jsonError.getString("customerUcn"));
                tvCustomerUcnError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("customerName")) {
                tvCustomerNameError.setText(jsonError.getString("customerName"));
                tvCustomerNameError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("ticketsCount")) {
                tvTicketsCountError.setText(jsonError.getString("ticketsCount"));
                tvTicketsCountError.setVisibility(View.VISIBLE);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}