package com.code.retrofit.network;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceManager {

    static Retrofit retrofit = null;

    private static ApiServiceManager mInstance;

    public static synchronized ApiServiceManager getInstance() {
        if (mInstance == null) {
            mInstance = new ApiServiceManager();
        }
        return mInstance;
    }

    public static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            @SuppressLint("CustomX509TrustManager") final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @SuppressLint("TrustAllX509TrustManager")
                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            EmptyBodyInterceptor emptyBodyInterceptor = new EmptyBodyInterceptor();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.callTimeout(2, TimeUnit.MINUTES);
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.readTimeout(30, TimeUnit.SECONDS);
            builder.writeTimeout(30, TimeUnit.SECONDS);
            builder.addInterceptor(interceptor);
            builder.addInterceptor(emptyBodyInterceptor);
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);

            return builder;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ApiInterface getAPIClient() {

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(Url.BASE_URL)
                    .client(getUnsafeOkHttpClient().build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }

    public void makePostRequestField(String url, Map<String, Object> map, final ResponseHandler handler) {
        try {

            ApiInterface apiInterface = ApiServiceManager.getAPIClient();
            Call<JsonObject> call = apiInterface.makePostRequestField(url, map);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    if (response.isSuccessful()) {

                        handler.onSuccess(response.body(), response.code());

                    } else {

                        handler.onError(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    handler.onFailureThrowable(t);

                }
            });
        } catch (Exception e) {

            handler.onFailureThrowable(e);

        }
    }

    public void makePostRequestBody(String url, Object obj, final ResponseHandler handler) {
        try {

            ApiInterface apiInterface = ApiServiceManager.getAPIClient();
            Call<JsonObject> call = apiInterface.makePostRequestBody(url, obj);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    if (response.isSuccessful()) {

                        Gson gson = new Gson();

                        handler.onSuccess(response.body(), response.code());
                    } else {

                        handler.onError(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    handler.onFailureThrowable(t);

                }
            });
        } catch (Exception e) {

            handler.onFailureThrowable(e);

        }
    }

    public void makeGetRequest(String url, final ResponseHandler handler) {
        try {

            ApiInterface apiInterface = ApiServiceManager.getAPIClient();
            Call<JsonObject> call = apiInterface.makeRequestGet(url);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    if (response.isSuccessful()) {


                        handler.onSuccess(response.body(), response.code());
                    } else {

                        handler.onError(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.onFailureThrowable(t);

                }
            });
        } catch (Exception e) {

            handler.onFailureThrowable(e);

        }
    }

    public void makeGetRequestWithPath(String url, String pathValue, final ResponseHandler handler) {
        try {

            ApiInterface apiInterface = ApiServiceManager.getAPIClient();
            Call<JsonObject> call = apiInterface.makeRequestGetWithValue(url, pathValue);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    if (response.isSuccessful()) {


                        handler.onSuccess(response.body(), response.code());
                    } else {

                        handler.onError(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.onFailureThrowable(t);

                }
            });
        } catch (Exception e) {

            handler.onFailureThrowable(e);

        }
    }

    public void makePatchRequestWithPath(String url, String pathValue, Map<String, Object> map, final ResponseHandler handler) {
        try {

            ApiInterface apiInterface = ApiServiceManager.getAPIClient();
            Call<JsonObject> call = apiInterface.makePatchRequest(url, pathValue, map);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    if (response.isSuccessful()) {


                        handler.onSuccess(response.body(), response.code());
                    } else {

                        handler.onError(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.onFailureThrowable(t);

                }
            });
        } catch (Exception e) {

            handler.onFailureThrowable(e);

        }
    }

    public void makePostRequestFieldWithHeader(String url, Map<String, Object> fieldMap, Map<String, Object> headerMap,
                                               final ResponseHandler handler) {
        try {

            ApiInterface apiInterface = ApiServiceManager.getAPIClient();
            Call<JsonObject> call = apiInterface.makePostRequestFieldWithHeader(url, fieldMap, headerMap);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    if (response.isSuccessful()) {


                        handler.onSuccess(response.body(), response.code());
                    } else {

                        handler.onError(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.onFailureThrowable(t);

                }
            });
        } catch (Exception e) {

            handler.onFailureThrowable(e);

        }
    }


    public void makePostRequestBodyWithHeader(String url,Object object, Map<String, Object> headerMap,
                                              final ResponseHandler handler) {
        try {

            ApiInterface apiInterface = ApiServiceManager.getAPIClient();
            Call<JsonObject> call = apiInterface.makePostRequestBodyWithHeader(url, object, headerMap);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {
                    if (response.isSuccessful()) {


                        handler.onSuccess(response.body(), response.code());
                    } else {

                        handler.onError(response.message(), response.code());
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    handler.onFailureThrowable(t);

                }
            });
        } catch (Exception e) {

            handler.onFailureThrowable(e);

        }
    }

    public static class EmptyBodyInterceptor implements Interceptor {

        @NonNull
        @Override
        public Response intercept(Chain chain) throws IOException {

            Response response = chain.proceed(chain.request());

            if (!response.isSuccessful()) {
                return response;
            }

            if (response.code() != 204 && response.code() != 205) {
                return response;
            }

            if ((response.body() != null ? response.body().contentLength() : -1) >= 0) {
                return response;
            }


            ResponseBody emptyBody = ResponseBody.create(MediaType.get("text/plain"), "");

            return response.newBuilder()
                    .code(200)
                    .build();
        }
    }

}
