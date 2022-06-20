package bg.tuvarna.ticketoffice.activities;

import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.LoginResponse;
import bg.tuvarna.ticketoffice.services.HttpClient;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {

    private static final HttpClient client = HttpClient.getInstance();

    public HttpClient getClient() {
        return client;
    }

    public void handleCommonMessage(Response<?> response) {
        try {
            CommonMessageResponse messageResponse = getClient().getConverter().convert(response.errorBody());
            Toast.makeText(BaseActivity.this, messageResponse.getMessage(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleUnauthorized() {
        logout();
        Toast.makeText(BaseActivity.this, "Session expired!", Toast.LENGTH_LONG).show();
    }

    public void logout() {
        client.setJwt(null);
        client.setUserRole(null);
        showPage(LoginActivity.class);
    }

    public void handleFailureRequest() {
        Toast.makeText(BaseActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
    }

    public void showPage(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(getApplicationContext(), activityClass);
        startActivity(intent);
    }
}