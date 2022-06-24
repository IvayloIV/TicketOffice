package bg.tuvarna.ticketoffice.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import java.net.HttpURLConnection;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.domain.models.requests.RateUserRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.UserProfileResponse;
import bg.tuvarna.ticketoffice.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDetailsActivity extends BaseActivity {

    private Long userId;
    private TextView tvNameInput;
    private TextView tvRoleInput;
    private TextView tvEventsFeeInput;
    private TextView tvEventsCountInput;
    private TextView tvActiveEventsInput;
    private TextView tvSoldTicketsInput;
    private TextView tvRatingInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        userId = getIntent().getLongExtra("userId", -1);
        tvNameInput = findViewById(R.id.user_details_tv_name);
        tvRoleInput = findViewById(R.id.user_details_role_value);
        tvEventsFeeInput = findViewById(R.id.user_details_events_fee_value);
        tvEventsCountInput = findViewById(R.id.user_details_events_count_value);
        tvActiveEventsInput = findViewById(R.id.user_details_active_events_value);
        tvSoldTicketsInput = findViewById(R.id.user_details_sold_tickets_value);
        tvRatingInput = findViewById(R.id.user_details_rating_value);

        TextView ratingBarText = findViewById(R.id.user_details_rate_text);
        RatingBar ratingBarValue = findViewById(R.id.user_details_rate_value);

        if (userId == -1) {
            findViewById(R.id.user_details_btn_back).setOnClickListener(view -> showPage(HomeActivity.class));
        } else {
            findViewById(R.id.user_details_btn_back).setOnClickListener(view -> showPage(DistributorListActivity.class));
        }

        if (getClient().getUserRole().equals(Role.DISTRIBUTOR)) {
            ratingBarText.setVisibility(View.INVISIBLE);
            ratingBarValue.setVisibility(View.INVISIBLE);
        } else if (getClient().getUserRole().equals(Role.ORGANISER) && userId == -1) {
            ratingBarText.setVisibility(View.INVISIBLE);
            ratingBarValue.setVisibility(View.INVISIBLE);
            findViewById(R.id.user_details_rating_text).setVisibility(View.INVISIBLE);
            tvRatingInput.setVisibility(View.INVISIBLE);
        } else {
            ratingBarValue.setOnRatingBarChangeListener((bar, rating, fromUser) -> {
                RateUserRequest rateUserRequest = new RateUserRequest();
                rateUserRequest.setUserId(userId);
                rateUserRequest.setRating(Float.valueOf(rating).intValue());

                UserService userService = getClient().getUserService();
                userService.rate(rateUserRequest, getClient().getAuthorizationHeader())
                    .enqueue(new Callback<CommonMessageResponse>() {
                        @Override
                        public void onResponse(Call<CommonMessageResponse> call, Response<CommonMessageResponse> response) {
                            int code = response.code();
                            if (code == HttpURLConnection.HTTP_CREATED) {
                                updateUser();
                                CommonMessageResponse messageResponse = response.body();
                                Toast.makeText(UserDetailsActivity.this, messageResponse.getMessage(), Toast.LENGTH_LONG).show();
                            } else if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
                                ratingBarValue.setRating(0);
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
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUser();
    }

    private void updateUser() {
        UserService userService = getClient().getUserService();
        Call<UserProfileResponse> userDetails;
        if (userId > -1) {
            userDetails = userService.details(userId, getClient().getAuthorizationHeader());
        } else {
            userDetails = userService.profile(getClient().getAuthorizationHeader());
        }

        userDetails
            .enqueue(new Callback<UserProfileResponse>() {
                @SuppressLint({"SetTextI18n", "DefaultLocale"})
                @Override
                public void onResponse(Call<UserProfileResponse> call, Response<UserProfileResponse> response) {
                    int code = response.code();
                    if (code == HttpURLConnection.HTTP_OK) {
                        UserProfileResponse user = response.body();
                        String roleStr = user.getRole().name();

                        tvNameInput.setText(user.getName());
                        tvRoleInput.setText(roleStr.charAt(0) + roleStr.substring(1).toLowerCase());
                        tvEventsFeeInput.setText(String.format("%.2f", user.getEventsFee()));
                        tvEventsCountInput.setText(String.valueOf(user.getEventsCount()));
                        tvActiveEventsInput.setText(String.valueOf(user.getActiveEvents()));
                        tvSoldTicketsInput.setText(String.valueOf(user.getSoldEventsTickets()));
                        tvRatingInput.setText(String.format("%.2f", user.getAverageRating()));
                    } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        handleUnauthorized();
                    }
                }

                @Override
                public void onFailure(Call<UserProfileResponse> call, Throwable t) {
                    handleFailureRequest();
                }
            });
    }
}