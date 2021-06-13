package com.saglissindustries.fairrepack.httprequests;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * Runnable utilisé par RequestHandler pour faire une requête asynchrone
 */
class RequestRunnable implements Runnable {
    public String url;
    public String method;
    public JSONObject body;
    /**
     * Runnable de callback appelé une fois que la requête est terminée
     */
    public RequestCallback callback;

    @Override
    public void run() {
        try {
            URL object = new URL(url);

            HttpURLConnection con = (HttpURLConnection) object.openConnection();
            con.setDoOutput(true);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Ecrit le JSON dans le corp de la requête
            OutputStream wr = con.getOutputStream();
            wr.write(body.toString().getBytes(StandardCharsets.UTF_8));
            wr.close();

            // Retourne une erreur si la requête n'a pas réussi
            if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
            }

            // Récupère la sortie
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                // Parse la sortie en JSON
                JSONObject response = new JSONObject(output);
                // Ajoute le JSON dans le callback
                callback.response = response;
                callback.run(); // Appelle le callback
            }

            con.disconnect();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}

public class RequestHandler extends Thread {
    String url;
    String method;
    JSONObject body;
    RequestCallback callback;

    public RequestHandler(String url, String method, JSONObject body, RequestCallback callback) {
        this.url = url;
        this.body = body;
        this.method = method;
        this.callback = callback;

        this.start();
    }

    @Override
    public void run() {
        // Créé le runnable qui effectue la requête
        RequestRunnable requestRunnable = new RequestRunnable();
        requestRunnable.url = this.url;
        requestRunnable.body = this.body;
        requestRunnable.method = this.method;
        requestRunnable.callback = this.callback;

        requestRunnable.run();
    }
}