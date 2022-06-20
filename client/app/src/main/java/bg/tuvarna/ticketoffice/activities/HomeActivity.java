package bg.tuvarna.ticketoffice.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentManager;

import java.net.HttpURLConnection;
import java.util.List;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.domain.models.responses.NotificationListResponse;
import bg.tuvarna.ticketoffice.fragments.DialogNotificationsFragment;
import bg.tuvarna.ticketoffice.services.NotificationService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity {

    private Button notificationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        notificationButton = findViewById(R.id.home_btn_notification);
        notificationButton.setOnClickListener(this::showNotifications);
        findViewById(R.id.home_btn_logout).setOnClickListener(view -> logout());
        findViewById(R.id.home_btn_profile).setOnClickListener(view -> logout());
        findViewById(R.id.home_btn_organizer_list).setOnClickListener(view -> logout());

        Role userRole = getClient().getUserRole();

        Button createButton = findViewById(R.id.home_btn_create);
        if (userRole.equals(Role.ORGANISER)) {
            createButton.setText(R.string.home_btn_create_event);
        } else if (userRole.equals(Role.DISTRIBUTOR)) {
            createButton.setOnClickListener(view -> showPage(CreateTicketActivity.class));
            createButton.setText(R.string.home_btn_create_ticket);
        }

        Button distributors = findViewById(R.id.home_btn_distributor_list);
        distributors.setOnClickListener(view -> logout());
        if (userRole.equals(Role.ORGANISER)) {
            distributors.setVisibility(View.VISIBLE);
        } else if (userRole.equals(Role.DISTRIBUTOR)) {
            distributors.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        NotificationService notificationService = getClient().getNotificationService();
        notificationService.notificationCount(getClient().getAuthorizationHeader())
            .enqueue(new Callback<Long>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    int code = response.code();

                    if (code == HttpURLConnection.HTTP_OK) {
                        Long notificationCount = response.body();

                        if (notificationCount == 0) {
                            notificationButton.setVisibility(View.INVISIBLE);
                        } else {
                            String msg = getApplicationContext().getResources().getString(R.string.home_btn_notification);
                            notificationButton.setText(msg + notificationCount);
                            notificationButton.setVisibility(View.VISIBLE);
                        }
                    } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handleUnauthorized();
                    }
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    handleFailureRequest();
                }
            });
    }

    private void showNotifications(View view) {
        NotificationService notificationService = getClient().getNotificationService();
        notificationService.notificationList(getClient().getAuthorizationHeader())
            .enqueue(new Callback<List<NotificationListResponse>>() {
                @Override
                public void onResponse(Call<List<NotificationListResponse>> call, Response<List<NotificationListResponse>> response) {
                    int code = response.code();

                    if (code == HttpURLConnection.HTTP_OK) {
                        notificationButton.setVisibility(View.INVISIBLE);
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        DialogNotificationsFragment dialogFriendsFragment = DialogNotificationsFragment.newInstance(response.body());
                        try {
                            dialogFriendsFragment.show(fragmentManager, "notifications_fragment");
                        } catch (IllegalStateException ex) {
                            ex.printStackTrace();
                        }
                    } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handleUnauthorized();
                    }
                }

                @Override
                public void onFailure(Call<List<NotificationListResponse>> call, Throwable t) {
                    handleFailureRequest();
                }
            });
    }
}