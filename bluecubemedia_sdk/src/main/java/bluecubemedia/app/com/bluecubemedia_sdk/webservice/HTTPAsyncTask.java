package bluecubemedia.app.com.bluecubemedia_sdk.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import bluecubemedia.app.com.bluecubemedia_sdk.model.ConvertDetail;
import bluecubemedia.app.com.bluecubemedia_sdk.wsinterface.WebServiceInterface;


public class HTTPAsyncTask extends AsyncTask<String,Void,ConvertDetail> {

    private String url;
    private String serviceMethod;
    private HTTPConnectivity httpConnectivity;
    private Context context;
    private Gson gson;
    private WebServiceInterface webServiceInterface;
    private String amount;

    public HTTPAsyncTask(Context context, String url, String serviceMethod,
                         WebServiceInterface webServiceInterface, String amount) {
        this.url = url;
        this.serviceMethod = serviceMethod;
        this.context =  context;
        httpConnectivity = HTTPConnectivity.getInstance();
        this.webServiceInterface = webServiceInterface;
        gson = new Gson();
        this.amount = amount;
    }



    @Override
    protected ConvertDetail doInBackground(String[] url) {
        String response = null;
        ConvertDetail convertDetail = null;
        if(httpConnectivity.isNetworkAvailable(context)){
            if(httpConnectivity.isOnline()) {
                response = httpConnectivity.callHttpConnectivity(url[0], serviceMethod);
                Log.d("TAG","response :::: " +response);
                convertDetail = gson.fromJson(response, ConvertDetail.class);
           }
        }
        return convertDetail;
    }

    @Override
    protected void onPostExecute(ConvertDetail convertDetail) {
        super.onPostExecute(convertDetail);
        //mWeatherUtilityInterface.getWeatherDetails(weatherDetails);

        double rate = Double.parseDouble(amount)* Double.parseDouble(convertDetail.getRate());
        webServiceInterface.getConvertedAmount(rate);

    }

}
