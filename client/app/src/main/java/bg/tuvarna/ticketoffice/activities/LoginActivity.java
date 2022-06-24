package bg.tuvarna.ticketoffice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.enums.Role;
import bg.tuvarna.ticketoffice.domain.models.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.LoginResponse;
import bg.tuvarna.ticketoffice.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    static final String LOGIN_USER_INFO = "LOGIN_USER_INFO";
    static final String LOGIN_PASSWORD = "LOGIN_PASSWORD";

    private EditText etUserLoginInput;
    private EditText etPassword;
    private TextView tvUsernameError;
    private TextView tvPasswordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserLoginInput = findViewById(R.id.login_tv_input);
        etPassword = findViewById(R.id.login_tv_password);
        tvUsernameError = findViewById(R.id.login_error_username);
        tvPasswordError = findViewById(R.id.login_error_password);

        findViewById(R.id.login_btn_login).setOnClickListener(view -> DoLogin());
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(LOGIN_USER_INFO, etUserLoginInput.getText().toString());
        outState.putString(LOGIN_PASSWORD, etPassword.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        etUserLoginInput.setText(outState.getString(LOGIN_USER_INFO));
        etPassword.setText(outState.getString(LOGIN_PASSWORD));
    }

    private void DoLogin() {
        String name = etUserLoginInput.getText().toString();
        String password = etPassword.getText().toString();
        tvUsernameError.setVisibility(View.INVISIBLE);
        tvPasswordError.setVisibility(View.INVISIBLE);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setName(name);
        loginRequest.setPassword(password);

        UserService userService = getClient().getUserService();
        Call<LoginResponse> user = userService.login(loginRequest);
        user.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int code = response.code();

                if (code == HttpURLConnection.HTTP_OK) {
                    LoginResponse loginResponse = response.body();
                    getClient().setUserRole(loginResponse.getRole());
                    getClient().setJwt(loginResponse.getJwtToken());

                    Class<? extends BaseActivity> activity;
                    if (loginResponse.getRole().equals(Role.ADMIN)) {
                        activity = RegisterActivity.class;
                    } else {
                        activity = HomeActivity.class;
                    }

                    Intent intent = new Intent(getApplicationContext(), activity);
                    startActivity(intent);
                } else if (code == 422) {
                    showErrorInputMessages(response);
                } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    handleCommonMessage(response);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                handleFailureRequest();
            }
        });
    }

    private void showErrorInputMessages(Response<LoginResponse> response) {
        try {
            JSONObject jsonError = new JSONObject(response.errorBody().string());
            if (jsonError.has("name")) {
                tvUsernameError.setText(jsonError.getString("name"));
                tvUsernameError.setVisibility(View.VISIBLE);
            }
            if (jsonError.has("password")) {
                tvPasswordError.setText(jsonError.getString("password"));
                tvPasswordError.setVisibility(View.VISIBLE);
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
    }
}