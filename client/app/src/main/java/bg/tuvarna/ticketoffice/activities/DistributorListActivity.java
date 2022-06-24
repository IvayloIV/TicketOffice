package bg.tuvarna.ticketoffice.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.adapters.DistributorListAdapter;
import bg.tuvarna.ticketoffice.domain.models.responses.DistributorListResponse;
import bg.tuvarna.ticketoffice.fragments.DialogDistributorListFragment;
import bg.tuvarna.ticketoffice.fragments.listeners.DistributorFilterDialogListener;
import bg.tuvarna.ticketoffice.services.DistributorService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DistributorListActivity extends BaseActivity implements DistributorFilterDialogListener {

    private RecyclerView distributorsRv;
    private String name;
    private Double rating;
    private Long soldTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor_list);

        distributorsRv = findViewById(R.id.dialog_distributor_list_rv);
        findViewById(R.id.distributor_list_btn_back).setOnClickListener(view -> showPage(HomeActivity.class));
        findViewById(R.id.distributor_list_btn_filters).setOnClickListener(view -> {
            FragmentManager fragmentManager = getSupportFragmentManager();
            DialogDistributorListFragment dialog = DialogDistributorListFragment.newInstance(name, rating, soldTickets);
            try {
                dialog.show(fragmentManager, "distributor_filter_fragment");
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

    private void userDetailsRedirect(Long userId) {
        Intent intent = new Intent(getApplicationContext(), UserDetailsActivity.class);
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public void setSoldTickets(Long soldTickets) {
        this.soldTickets = soldTickets;
    }

    @Override
    public void filterResult() {
        DistributorService distributorService = getClient().getDistributorService();
        Consumer<Long> userDetailsRedirect = this::userDetailsRedirect;
        Context context = getApplicationContext();

        Map<String, String> filters = new HashMap<>();
        if (name != null) {
            filters.put("name", name);
        }
        if (rating != null) {
            filters.put("rating", rating.toString());
        }
        if (soldTickets != null) {
            filters.put("soldTickets", soldTickets.toString());
        }

        distributorService.list(filters, getClient().getAuthorizationHeader())
            .enqueue(new Callback<List<DistributorListResponse>>() {
                @Override
                public void onResponse(Call<List<DistributorListResponse>> call, Response<List<DistributorListResponse>> response) {
                    int code = response.code();
                    if (code == HttpURLConnection.HTTP_OK) {
                        List<DistributorListResponse> distributors = response.body();

                        DistributorListAdapter adapter = new DistributorListAdapter(distributors, userDetailsRedirect);
                        distributorsRv.setLayoutManager(new LinearLayoutManager(context));
                        distributorsRv.setAdapter(adapter);
                    } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handleUnauthorized();
                    }
                }

                @Override
                public void onFailure(Call<List<DistributorListResponse>> call, Throwable t) {
                    handleFailureRequest();
                }
            });
    }
}