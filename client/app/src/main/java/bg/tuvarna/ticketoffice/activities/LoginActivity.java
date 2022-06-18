package bg.tuvarna.ticketoffice.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.requests.LoginRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.LoginResponse;
import bg.tuvarna.ticketoffice.services.HttpClient;
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

//    private ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
//            new ActivityResultContracts.StartActivityForResult(),
//            LoginActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUserLoginInput = findViewById(R.id.login_tv_input);
        etPassword = findViewById(R.id.login_tv_password);
        tvUsernameError = findViewById(R.id.login_error_username);
        tvPasswordError = findViewById(R.id.login_error_password);

        findViewById(R.id.login_btn_login).setOnClickListener(view -> DoLogin());

        if (Debug.isDebuggerConnected()) {
            SetDebugInfo();
        }
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

    @SuppressLint("SetTextI18n")
    private void SetDebugInfo() {
        etUserLoginInput.setText("admin");
        etPassword.setText("1234");
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
        Call<LoginResponse> user = userService.create(loginRequest);
        user.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                int code = response.code();

                if (code == HttpURLConnection.HTTP_OK) {
                    LoginResponse loginResponse = response.body();
                    getClient().setUserRole(loginResponse.getRole());
                    getClient().setJwt(loginResponse.getJwtToken());
                    System.out.println(loginResponse.getRole());
                    // Intent to another page
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


//        ServerRequest<User> request = new ServerRequest<>(OperationType.USER_LOGIN);
//        request.setData(model);
//        getNetClient().sendRequest(request);

//        if (response.getOperationType() == OperationType.USER_LOGIN) {
//        CompleteActivity(response);
//        }

//        Intent intent = new Intent(this, HomeActivity.class);
//        startActivity( intent );
//
//        finish();
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