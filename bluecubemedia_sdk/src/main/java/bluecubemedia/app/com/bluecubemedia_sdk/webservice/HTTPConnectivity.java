package bluecubemedia.app.com.bluecubemedia_sdk.webservice;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPConnectivity {

    private static HTTPConnectivity httpConnectivity;

    public static HTTPConnectivity getInstance(){
        if(null==httpConnectivity){
            httpConnectivity=new HTTPConnectivity();
        }
        return httpConnectivity;
    }

    public boolean isNetworkAvailable( Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    public String callHttpConnectivity(String serviceUrl, String serviceMethod){
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        String response = null;
        try {
            URL url = new URL(serviceUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                 urlConnection.setRequestProperty("Accept", "application/json");
                 urlConnection.setRequestMethod("GET");
            int statusCode = urlConnection.getResponseCode();
            if (statusCode ==  200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                response = convertInputStreamToString(inputStream);
            }
        } catch (Exception e) {
            e.getLocalizedMessage();
        }
        urlConnection.disconnect();
        return response;
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line;
        }
        if(null!=inputStream){
            inputStream.close();
        }
        return result;
    }
}

