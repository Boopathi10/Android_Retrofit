package com.code.retrofit.network;

public interface ResponseHandler {

    void onSuccess(Object data,int responseCode);

    void onFailureThrowable(Throwable t);

    void onError(String msg,int responseCode);


}
