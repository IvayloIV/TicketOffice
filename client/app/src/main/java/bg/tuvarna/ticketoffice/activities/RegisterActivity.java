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

import bg.tuvarna.ticketoffice.R;
import bg.tuvarna.ticketoffice.domain.models.requests.RegisterRequest;
import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.services.UserService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity {

    private Spinner cb;
    private EditText etUsernameInput;
    private TextView tvUsernameError;
    private EditText etPasswordInput;
    private TextView tvPasswordError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        cb = findViewById(R.id.reg_sp_role);
        etUsernameInput = findViewById(R.id.reg_tv_username);
        tvUsernameError = findViewById(R.id.reg_error_username);
        etPasswordInput = findViewById(R.id.reg_tv_password);
        tvPasswordError = findViewById(R.id.reg_error_password);

        findViewById(R.id.reg_bt_logout).setOnClickListener(view -> logout());
        findViewById(R.id.reg_btn_registration).setOnClickListener(view -> DoRegister());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cb.setAdapter(adapter);
    }

    private void DoRegister() {
        String name = etUsernameInput.getText().toString();
        String password = etPasswordInput.getText().toString();
        String role = ((TextView) cb.getSelectedView()).getText().toString();
        tvUsernameError.setVisibility(View.INVISIBLE);
        tvPasswordError.setVisibility(View.INVISIBLE);

        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setPassword(password);
        registerRequest.setRole(role.toUpperCase());

        UserService userService = getClient().getUserService();
        String authorizationHeader = getClient().getAuthorizationHeader();
        Call<CommonMessageResponse> registerCall = userService.register(registerRequest, authorizationHeader);
        registerCall.enqueue(new Callback<CommonMessageResponse>() {
            @Override
            public void onResponse(Call<CommonMessageResponse> call, Response<CommonMessageResponse> response) {
                int code = response.code();

                if (code == HttpURLConnection.HTTP_CREATED) {
                    CommonMessageResponse commonMessageResponse = response.body();
                    etUsernameInput.setText("");
                    etPasswordInput.setText("");
                    Toast.makeText(RegisterActivity.this, commonMessageResponse.getMessage(), Toast.LENGTH_LONG).show();
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