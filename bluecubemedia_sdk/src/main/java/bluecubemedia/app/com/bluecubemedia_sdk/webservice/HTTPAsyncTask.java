package bluecubemedia.app.com.bluecubemedia_sdk.webservice;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import bluecubemedia.app.com.bluecubemedia_sdk.database.DBAdapter;
import bluecubemedia.app.com.bluecubemedia_sdk.model.ConvertDetail;
import bluecubemedia.app.com.bluecubemedia_sdk.wsinterface.WebServiceInterface;


public class HTTPAsyncTask extends AsyncTask<String, Void, ConvertDetail> {

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
        this.context = context;
        httpConnectivity = HTTPConnectivity.getInstance();
        this.webServiceInterface = webServiceInterface;
        gson = new Gson();
        this.amount = amount;
    }


    @Override
    protected ConvertDetail doInBackground(String[] url) {
        String response = null;
        ConvertDetail convertDetail = null;
        if (httpConnectivity.isNetworkAvailable(context)) {
            if (httpConnectivity.isOnline()) {
                Log.d("TAG", "url" + url[0]);
                response = httpConnectivity.callHttpConnectivity(url[0], serviceMethod);
                if (response != null) {
                    Log.d("TAG", "response :::: " + response);
                    convertDetail = gson.fromJson(response, ConvertDetail.class);
                }
            }
        }
        return convertDetail;
    }

    @Override
    protected void onPostExecute(ConvertDetail convertDetail) {
        super.onPostExecute(convertDetail);
        double rate = 0;
       if( convertDetail!=null){        //mWeatherUtilityInterface.getWeatherDetails(weatherDetails);
        rate = Double.parseDouble(amount) * Double.parseDouble(convertDetail.getRate());
        webServiceInterface.getConvertedAmount(rate);
       }else{
           Toast.makeText(context,"Server Error", Toast.LENGTH_LONG).show();
           webServiceInterface.getConvertedAmount(rate);
       }
    }

}
