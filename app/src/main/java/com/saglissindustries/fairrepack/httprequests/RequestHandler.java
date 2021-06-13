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
    public String token;
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
            if (!method.equals("GET") && !method.equals("get")) {
                con.setDoOutput(true); // implicitly set the request method to POST
            }
            con.setRequestMethod(method);
            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            // Rajoute le token dans les en-têtes de la requête s'il est défini
            if (this.token != null) {
                con.setRequestProperty("Authorization", "Bearer " + token);
            }

            // Ecrit le JSON dans le corp de la requête
            if (this.body != null) {
                OutputStream wr = con.getOutputStream();
                wr.write(body.toString().getBytes(StandardCharsets.UTF_8));
                wr.close();
            }

            // Retourne une erreur si la requête n'a pas réussi
            int responseCode = con.getResponseCode();
            if (responseCode != 200 && responseCode != 201 && responseCode != 202 && responseCode != 204) {
                throw new RuntimeException("Failed : HTTP error code : " + con.getResponseCode());
            }

            // Récupère la sortie
            BufferedReader br = new BufferedReader(new InputStreamReader((con.getInputStream())));

            String output;
            while ((output = br.readLine()) != null) {
                // Parse la sortie en JSON
                // Ajoute le JSON dans le callback
                callback.response = new JSONObject(output);
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
    String token;
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

    public RequestHandler(String url, String method, JSONObject body, String token, RequestCallback callback) {
        this.url = url;
        this.body = body;
        this.token = token;
        this.method = method;
        this.callback = callback;

        this.start();
    }

    public RequestHandler(String url, String method, RequestCallback callback) {
        this.url = url;
        this.method = method;
        this.callback = callback;

        this.start();
    }

    public RequestHandler(String url, String method, String token, RequestCallback callback) {
        this.url = url;
        this.token = token;
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
        requestRunnable.token = this.token;
        requestRunnable.method = this.method;
        requestRunnable.callback = this.callback;

        requestRunnable.run();
    }
}