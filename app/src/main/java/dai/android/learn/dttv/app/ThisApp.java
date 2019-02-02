package dai.android.learn.dttv.app;

import android.app.Application;

public class ThisApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        UrlManager.get(getApplicationContext());
    }
}
