package bluecubemedia.app.com.currencyconverter.Utility;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import bluecubemedia.app.com.currencyconverter.R;

/**
 * Created by arun on 26/02/16.
 */
public class CustomSpinnerListener implements AdapterView.OnItemSelectedListener {
    private Spinner spView;
    private Context mContext;
    public CustomSpinnerListener(Context mContext, Spinner spView){
        this.spView=spView;
        this.mContext = mContext;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }
}
