package model;

public interface ModelCallback {
    void onResponse(String response);
    void onError(String error);
}
