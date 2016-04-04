package bluecubemedia.app.com.currencyconverter;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;


public class BaseActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private final int mBaseLayout = R.layout.baselayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.baselayout);
        setTitle(null);
        frameLayout = (FrameLayout)findViewById(R.id.basecontainer);
        Toolbar topToolBar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        topToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    @Override
    public void setContentView(int layoutResID) {
        if (layoutResID == mBaseLayout) {
            View content = getWindow().findViewById(android.R.id.content);
            if (content != null) {
                super.setContentView(mBaseLayout);
            }
        }else {
            LayoutInflater inflater = getLayoutInflater();
            inflater.inflate(layoutResID, frameLayout);
            if(this instanceof CurrencyConverterActivity){
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            }
        }

    }
}
