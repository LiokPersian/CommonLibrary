package com.udac.coders.commonallinonelibrary;
import android.util.Log;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
public class LiokRestClient extends GenerateJwt {
    public JSONObject HttpRequest(String url, String method,
                                  List<NameValuePair> params) {
        InputStream is = null;
        JSONObject jObj = null;
        String json = "";

        // Making HTTP request
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse httpResponse = null;
            // check for request method
            if(method == "POST"){
                HttpPost httpPost = new HttpPost(url);
                httpPost.setHeader("Authorization", "Bearer "+Generate_Jwt());
                httpPost.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
                httpResponse= httpClient.execute(httpPost);
            }else if(method == "GET"){
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
                httpGet.setHeader("Authorization", "Bearer "+Generate_Jwt());
                httpResponse= httpClient.execute(httpGet);
            }
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, StandardCharsets.ISO_8859_1), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            jObj = new JSONObject(json);
        } catch (UnsupportedEncodingException  | ClientProtocolException e) {
            e.printStackTrace();
        }   catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e);
        }
        return jObj;
    }
}