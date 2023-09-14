package com.code.retrofit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.code.retrofit.R;
import com.code.retrofit.model.SampleModel;
import com.code.retrofit.network.ApiServiceManager;
import com.code.retrofit.network.ResponseHandler;
import com.code.retrofit.network.Url;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //sample
    String userId,userType,offset,LIMIT;

    public static final String TAG ="MAIN ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void POST_FIELD_API_CALL() {

        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        map.put("user_type",userType);
        map.put("start",String.valueOf(offset));
        map.put("limit", String.valueOf(LIMIT));

        ResponseHandler responseHandler = new ResponseHandler() {
            @Override
            public void onSuccess(Object object,int responseCode) {

                try {
                    SampleModel item = new Gson().fromJson(object.toString(), SampleModel.class);
                    Log.d(TAG,"success = " + item.getSuccess());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureThrowable(Throwable t) {
                Log.d(TAG,"### onFailureThrowable msg = " + t.getMessage());
            }

            @Override
            public void onError(String msg,int responseCode) {
                Log.d(TAG,"### onError msg = " + msg);
                Log.d(TAG,"### onError responseCode = " + responseCode);
            }
        };

        ApiServiceManager.getInstance().makePostRequestField( Url.URL_PATH,map, responseHandler);

    }

}