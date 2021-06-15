package com.saglissindustries.fairrepack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

    private Button signInBtn, quit;
    private EditText loginText, passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signInBtn = (Button)findViewById(R.id.sign_in);
        loginText = (EditText)findViewById(R.id.login);
        passwordText = (EditText) findViewById(R.id.password);
        quit = (Button) findViewById(R.id.quit);

        // Gestion SharedPreferences pour Stockage Identifiants
        SharedPreferences sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);

        signInBtn.setOnClickListener((View view) -> {
            try {
                // Création du JSON pour la requête
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email", loginText.getText().toString());
                postDataParams.put("password", passwordText.getText().toString());

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

            String url = "https://pa.quozul.dev/api/user/read.php?id=1b86f141-11b4-4ac9-aa53-9ebb5584deb5";

            // Faire la requête
            new RequestHandler(url, "GET", sharedPreferences.getString("token", null), new RequestCallback() {
                @Override
                public void run() {
                    System.out.println(response.toString());
                }
            });
        });

        quit.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(getString(R.string.pop_title))
                    .setMessage(getString(R.string.pop_content))
                    .setPositiveButton(getString(R.string.yes), (dialog, which) -> finish())
                    .setNegativeButton(getString(R.string.no), (dialog, which) -> {})
                    .setCancelable(false)
                    .show();
        });
    }
}