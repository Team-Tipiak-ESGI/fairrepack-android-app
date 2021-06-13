package com.saglissindustries.fairrepack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.saglissindustries.fairrepack.httprequests.RequestCallback;
import com.saglissindustries.fairrepack.httprequests.RequestHandler;

import org.json.JSONException;
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

        // Creation Stockage Identifiants
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        this.signInBtn.setOnClickListener((View view) -> {
            try {
                // Création du JSON pour la requête
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email", this.loginText.getText().toString());
                postDataParams.put("password", this.passwordText.getText().toString());

                String url = "https://pa.quozul.dev/api/user/login.php";

                // Faire la requête
                new RequestHandler(url, "POST", postDataParams, new RequestCallback() {
                    @Override
                    public void run() {
                        try {
                            // Sauvegarde du token dans sharedPreferences
                            String token = response.get("token").toString();
                            sharedPreferences.edit().putString("token", token).apply();
                            System.out.println(token);
                        } catch (JSONException e) {
                            // Le JSON retourné par la requête est invalide
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                // Le JSON créé est invalide
                e.printStackTrace();
            }
        });

    }
}