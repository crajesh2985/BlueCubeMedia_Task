package bluecubemedia.app.com.currencyconverter;

import android.app.Application;
import android.content.Context;

public class AppSetting extends Application {
    public static Context context;

    @Override public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
