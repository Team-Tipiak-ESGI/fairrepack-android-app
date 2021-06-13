package com.saglissindustries.fairrepack.httprequests;

import org.json.JSONObject;

/**
 * Callback appelé une fois qu'une requête est terminée
 */
public abstract class RequestCallback implements Runnable {
    public JSONObject response; // Contient le JSON de retour de la requête

    public RequestCallback() {}

    public void run() {}
}