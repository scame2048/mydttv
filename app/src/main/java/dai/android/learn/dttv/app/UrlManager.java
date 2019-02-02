package dai.android.learn.dttv.app;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dai.android.learn.dttv.app.data.UrlInfo;


public final class UrlManager {
    private static final String TAG = UrlManager.class.getSimpleName();

    private static final String PATH = "iptv_source.json";

    private static UrlManager instance = null;

    public static UrlManager get(Context context) {
        if (null == instance) {
            synchronized (UrlManager.class) {
                if (null == instance) {
                    instance = new UrlManager(context);
                }
            }
        }
        return instance;
    }

    public static UrlManager get() {
        if (null == instance) {
            throw new RuntimeException("call get(Context) first");
        }
        return instance;
    }

    private final List<UrlInfo> mUrls;
    private final Context mContext;

    private UrlManager(Context context) {
        mContext = context;
        mUrls = new ArrayList<>();
    }

    public List<UrlInfo> getUrls() {
        return mUrls;
    }

    private void parseJson() {
        String strUrls = readAssetFile(mContext, PATH);
        if (TextUtils.isEmpty(strUrls)) {
            return;
        }

        mUrls.clear();

        JsonParser parser = new JsonParser();
        JsonArray jsonArray = parser.parse(strUrls).getAsJsonArray();

        Gson gson = new Gson();
        for (JsonElement element : jsonArray) {
            UrlInfo one = gson.fromJson(element, UrlInfo.class);
            mUrls.add(one);
        }
    }

    private static String readAssetFile(Context context, String file) {
        try {
            InputStream istream = context.getAssets().open(file);
            int size = istream.available();
            byte[] buffer = new byte[size];
            int read = istream.read(buffer);
            istream.close();
            // context.getAssets().close();
            return new String(buffer, "utf-8");
        } catch (Exception ignore) {
            Log.e(TAG, "can't read assets file from: " + file);
        }
        return null;
    }
}
