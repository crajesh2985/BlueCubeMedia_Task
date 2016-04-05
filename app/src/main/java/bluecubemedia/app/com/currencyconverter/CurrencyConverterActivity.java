package bluecubemedia.app.com.currencyconverter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import java.util.Locale;

import bluecubemedia.app.com.bluecubemedia_sdk.database.DBAdapter;
import bluecubemedia.app.com.bluecubemedia_sdk.webservice.HTTPAsyncTask;
import bluecubemedia.app.com.bluecubemedia_sdk.webservice.HTTPConnectivity;
import bluecubemedia.app.com.bluecubemedia_sdk.wsinterface.WebServiceInterface;
import bluecubemedia.app.com.currencyconverter.constants.Constants;

public class CurrencyConverterActivity extends BaseActivity implements WebServiceInterface{
    private LocationManager locManager;
    private LocationListener locListener = new MyLocationListener();
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private TextView tv_address;
    private Spinner sp_from;
    private Spinner sp_to;
    private Button btn_convert;
    private EditText et_amount;
    private TextView tv_rate;
    private String mServerURL;
    private ProgressBar progressBar;
    private HTTPConnectivity httpConnectivity;
    private String from;
    private String to;
    private String amount;
    private DBAdapter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DBAdapter(this);

        httpConnectivity = HTTPConnectivity.getInstance();
        tv_address = (TextView) findViewById(R.id.tv_address);
        sp_from = (Spinner) findViewById(R.id.spinner_from);
        sp_to = (Spinner) findViewById(R.id.spinner_to);
        btn_convert = (Button) findViewById(R.id.btn_convert);
        et_amount =(EditText) findViewById(R.id.et_amount);
        tv_rate = (TextView)findViewById(R.id.tv_converted_amount);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                from = currencyCode(sp_from.getSelectedItem().toString());
                to = currencyCode(sp_to.getSelectedItem().toString());
                amount = et_amount.getText().toString();

                if(httpConnectivity.isNetworkAvailable(AppSetting.context)){
                    if(httpConnectivity.isOnline()) {
                        callWebservice(from,to,amount);
                    }else {
                        showErrorMsg();
                    }
                }else {
                    showErrorMsg();
                }
            }
        });

        sp_to.setSelection(28);

        locManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            Toast.makeText(this, "Sorry, location is not determined. Please enable location providers" , Toast.LENGTH_LONG).show();
        }

        if (gps_enabled) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        }
        if (network_enabled) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
        }
    }

    private String currencyCode(String currencyValue){
        String[] value = currencyValue.split(":");
        return value[0].trim();
    }

    private void showErrorMsg() {
        progressBar.setVisibility(View.GONE);
        Toast.makeText(this, getResources().getString(R.string.no_network), Toast.LENGTH_SHORT).show();
        //error_msg.setVisibility(View.VISIBLE);
    }

    @Override
    public void getConvertedAmount(Double rate) {
        progressBar.setVisibility(View.GONE);
        db.open();
        db.addInHistoryRequest(from,to,amount,rate.toString());
        db.close();
        tv_rate.setText(""+rate);
    }

    private void callWebservice(String fromCurrency, String toCurrency, String amount){
        mServerURL = String.format("%sfrom=%s&to=%s",
                Constants.BASEURL, fromCurrency, toCurrency);
        HTTPAsyncTask httpAsyncTask = new HTTPAsyncTask(AppSetting.context,mServerURL, "GET", this, amount);
        httpAsyncTask.execute(mServerURL);
    }

    class MyLocationListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                if (ActivityCompat.checkSelfPermission(AppSetting.context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(AppSetting.context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locManager.removeUpdates(locListener);
                Geocoder geocoder = new Geocoder(AppSetting.context, Locale.ENGLISH);
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    if (addresses != null) {
                        Address returnedAddress = addresses.get(0);
                        StringBuilder strReturnedAddress = new StringBuilder("Address:\n");
                        for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
                            strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                        }
                        tv_address.setText("Current Location\n"+returnedAddress.getCountryName() + " - " + returnedAddress.getCountryCode());
                        Log.d("TAG","hiiii  ::: "+ returnedAddress.getCountryName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }


}
