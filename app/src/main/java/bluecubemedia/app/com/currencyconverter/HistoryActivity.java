package bluecubemedia.app.com.currencyconverter;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import bluecubemedia.app.com.bluecubemedia_sdk.database.DBAdapter;
import bluecubemedia.app.com.bluecubemedia_sdk.database.DBConstants;
import bluecubemedia.app.com.bluecubemedia_sdk.model.HistoryData;
import bluecubemedia.app.com.bluecubemedia_sdk.webservice.HTTPAsyncTask;
import bluecubemedia.app.com.bluecubemedia_sdk.webservice.HTTPConnectivity;
import bluecubemedia.app.com.bluecubemedia_sdk.wsinterface.WebServiceInterface;
import bluecubemedia.app.com.currencyconverter.constants.Constants;

public class HistoryActivity extends BaseActivity{
    private DBAdapter db;

    private List<HistoryData> mHistoryDataList;
    private RecyclerView mRecyclerView;
    private TextView mNoHistoryTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        mNoHistoryTV=(TextView)findViewById(R.id.noHistoryTV);


        db = new DBAdapter(this);
        db.open();
        Cursor claimHistoryCursor=db.getHistoryDetails();
        mHistoryDataList = new ArrayList<>();
        if (claimHistoryCursor.getCount() > 0) {
            claimHistoryCursor.moveToFirst();
            int fromIndex = claimHistoryCursor.getColumnIndex(DBConstants.COL_FROM);
            int toIndex = claimHistoryCursor.getColumnIndex(DBConstants.COL_TO);
            int amountIndex = claimHistoryCursor.getColumnIndex(DBConstants.COL_AMOUNT);
            int rateIndex = claimHistoryCursor.getColumnIndex(DBConstants.COL_FINIAL_RATE);
            for (int i = 0; i < claimHistoryCursor.getCount(); i++) {
                HistoryData historyData = new HistoryData();
                historyData.setFrom(claimHistoryCursor.getString(fromIndex));
                historyData.setTo(claimHistoryCursor.getString(toIndex));
                historyData.setAmount(claimHistoryCursor.getString(amountIndex));
                historyData.setFinial_rate(claimHistoryCursor.getString(rateIndex));
                mHistoryDataList.add(historyData);
                claimHistoryCursor.moveToNext();
            }
        }
        claimHistoryCursor.close();
        db.close();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        HistoryRecyclerAdapter adapter = new HistoryRecyclerAdapter(HistoryActivity.this,mHistoryDataList);
        mRecyclerView.setAdapter(adapter);

    }


}
