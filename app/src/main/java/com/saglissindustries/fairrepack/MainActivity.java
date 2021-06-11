package com.saglissindustries.fairrepack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.saglissindustries.fairrepack.httprequests.RequestHandler;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private Button signInBtn;
    private EditText loginText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.signInBtn = findViewById(R.id.sign_in);
        this.loginText = findViewById(R.id.login);
        this.passwordText = findViewById(R.id.password);

        RequestAsync connection = new RequestAsync();
        connection.doInBackground();

        // Creation Stockage Identifiants
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email", loginText);
                postDataParams.put("password", passwordText);

                return RequestHandler.sendPost("https://pa.quozul.dev/api/user/login.php", postDataParams);
            } catch (Exception e) {
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
        }
    }
}