package com.code.retrofit.network;

import com.google.gson.JsonObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface ApiInterface {

    @FormUrlEncoded
    @POST
    Call<JsonObject> makePostRequestField(
            @Url String url,
            @FieldMap Map<String,Object> map
    );

    @POST
    Call<JsonObject> makePostRequestBody(
            @Url String url,
            @Body Object obj
    );

    @POST
    Call<JsonObject> makePostRequestBodyWithHeader(
            @Url String url,
            @Body Object obj,
            @HeaderMap Map<String,Object> headerMap
    );

    @FormUrlEncoded
    @POST
    Call<JsonObject> makePostRequestFieldWithHeader(
            @Url String url,
            @FieldMap Map<String,Object> map,
            @HeaderMap Map<String,Object> headerMap
    );


    @GET
    Call<JsonObject> makeRequestGet(
            @Url String url
    );

    @GET
    Call<JsonObject> makeRequestGetWithValue(
            @Url String url,@Path("value") String value
    );

    @PATCH
    Call<JsonObject> makePatchRequest( @Url String url,@Path("value") String value, @Body() Map<String, Object> map);

}
