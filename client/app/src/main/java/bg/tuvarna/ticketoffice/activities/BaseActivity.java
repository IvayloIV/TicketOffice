package bg.tuvarna.ticketoffice.activities;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import bg.tuvarna.ticketoffice.domain.models.responses.CommonMessageResponse;
import bg.tuvarna.ticketoffice.domain.models.responses.LoginResponse;
import bg.tuvarna.ticketoffice.services.HttpClient;
import retrofit2.Response;

public class BaseActivity extends AppCompatActivity {

    private static final HttpClient client = new HttpClient();

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
        Toast.makeText(BaseActivity.this, "Session expired!", Toast.LENGTH_LONG).show();
        // Intent to login page
    }

    public void handleFailureRequest() {
        Toast.makeText(BaseActivity.this, "Something went wrong.", Toast.LENGTH_LONG).show();
    }
}