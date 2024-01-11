package com.udac.coders.commonallinonelibrary;
import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LiokVolleyService extends GenerateJwt{
    IResult mResultCallback;
    Context mContext;
    private final String TAG = "LiokVolleyService";
    public LiokVolleyService(IResult resultCallback, Context context){
        mResultCallback = resultCallback;
        mContext = context;
    }
    public void PostRequestString(String requestType, String url, Map<String,String> params){
        try{
            RequestQueue queue = Volley.newRequestQueue(mContext);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            mResultCallback.notifySuccess(requestType,jsonObject);
                        } catch (JSONException e) {
                            Log.d(TAG, "Volley requester " + requestType);
                            Log.d(TAG, "Volley JSON post" + e);
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        mResultCallback.notifyError(requestType,error);
                        Log.d(TAG, "Volley requester " + requestType);
                        Log.d(TAG, "Volley JSON post" + error);
                    }){
                @Override
                protected Map<String,String> getParams(){
                    return params;
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + Generate_Jwt());
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        }catch(Exception e){
            Log.d(TAG, "Volley requester " + requestType);
            Log.d(TAG, "Volley JSON post" + e);
        }

    }

    public void PostRequest(final String requestType, String url, JSONObject sendObj){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url,sendObj, response -> {
                mResultCallback.notifySuccess(requestType,response);
            }, error -> {
                mResultCallback.notifyError(requestType,error);
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + error);
            });

            queue.add(jsonObj);

        }catch(Exception e){
            Log.d(TAG, "Volley requester " + requestType);
            Log.d(TAG, "Volley JSON post" + e);
        }
    }

    public void GetRequest(final String requestType, String url, String... userId){
        try {
            RequestQueue queue = Volley.newRequestQueue(mContext);
            JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.GET,url,null, response -> {
                mResultCallback.notifySuccess(requestType, response);
            }, error -> {
                mResultCallback.notifyError(requestType, error);
                Log.d(TAG, "Volley requester " + requestType);
                Log.d(TAG, "Volley JSON post" + error);
            }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer " + Generate_Jwt());
                    return params;
                }
            };
            queue.add(jsonObj);
        }catch(Exception e){
            Log.d(TAG, "Volley requester " + requestType);
            Log.d(TAG, "Volley JSON post" + e);
        }
    }

}
