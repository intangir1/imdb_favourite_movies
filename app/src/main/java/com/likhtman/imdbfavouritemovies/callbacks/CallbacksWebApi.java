package com.likhtman.imdbfavouritemovies.callbacks;

import org.json.JSONException;

public interface CallbacksWebApi {
    void onAboutToBegin();
    void onSuccess(Object downloadedText) throws JSONException;
    void onError(int httpStatusCode, String errorMessage);
}
