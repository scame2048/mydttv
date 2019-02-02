package dai.android.learn.dttv.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import app.dttv.dttvlib.MediaPlayer;
import dai.android.learn.dttv.app.data.UrlInfo;

// 一些相关播放地址网站
//  https://www.colabug.com/4989352.html
//  https://cloud.tencent.com/developer/article/1361856
//  https://sixu.life/hd-live-source.html


public class MainActivity extends Activity implements
        SurfaceHolder.Callback2,
        MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener,
        MediaPlayer.OnBufferingUpdateListener,
        MediaPlayer.OnErrorListener {
    private static final String TAG = "MainActivity";

    private long mLastPressBackTime = 0L;

    private SurfaceView mVideoDisplay;
    private MediaPlayer mPlayer;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mLastPressBackTime <= 700) {
            super.onBackPressed();
        }
        mLastPressBackTime = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupMediaPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "Activity: [onDestroy]");

        // release player
        releaseMediaPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.d(TAG, "Activity: [onStop]");

        if (null != mPlayer) {
            mPlayer.stop();
        }
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "Activity: [onResume]");

        if (null != mPlayer) {
            mPlayer.start();
        }

        super.onResume();
    }

    private void setupMediaPlayer() {
        mPlayer = new MediaPlayer(this, true);

        mVideoDisplay = findViewById(R.id.video_display);
        mVideoDisplay.getHolder().addCallback(this);

        // setup listener
        mPlayer.setOnPreparedListener(this);
        mPlayer.setOnCompletionListener(this);
        mPlayer.setOnBufferingUpdateListener(this);
    }

    private void startMediaPlayer() {
        Log.d(TAG, "[startMediaPlayer]");
        try {
            if (null == mPlayer) {
                Log.w(TAG, "empty player");
                return;
            }

            if (mPlayer.isLooping()) {
                Log.d(TAG, "player is playing");
                return;
            }

            // 浙江卫视
            // mPlayer.setDataSource("http://223.110.243.173/ott.js.chinamobile.com/PLTV/3/224/3221227215/index.m3u8");

            // cctv 1
            // mPlayer.setDataSource("rtsp://119.39.49.116:554/ch00000090990000001022.sdp?vcdnid=001");

            // cctv 9
            // mPlayer.setDataSource("rtsp://119.39.49.116:554/ch00000090990000001020.sdp?vcdnid=001");
            // mPlayer.setDataSource("http://112.50.243.7/PLTV/88888888/224/3221226566/index.m3u8");

            // 经典电影
            // mPlayer.setDataSource("http://183.207.249.14/PLTV/3/224/3221225567/index.m3u8");

            // newTv 动作电影
            // mPlayer.setDataSource("http://183.207.249.15/PLTV/3/224/3221225529/index.m3u8");

            // 新闻频道
            mPlayer.setDataSource("http://112.50.243.7/PLTV/88888888/224/3221226507/index.m3u8");

            // iptv 经典剧场
            // mPlayer.setDataSource("rtsp://119.39.49.116:554/ch00000090990000001186.sdp?vcdnid=001");

            // 一个轮播
            // mPlayer.setDataSource("http://112.50.243.7/PLTV/88888888/224/3221226670/index.m3u8");

            // 海外剧场
            // mPlayer.setDataSource("http://183.207.249.15/PLTV/3/224/3221225547/index.m3u8");

            // 湖南电影
            // mPlayer.setDataSource("rtsp://119.39.49.116:554/ch00000090990000001192.sdp?vcdnid=001");

            // 苹果测试
            // mPlayer.setDataSource("http://devimages.apple.com.edgekey.net/streaming/examples/bipbop_4x3/gear2/prog_index.m3u8");
            mPlayer.setDisplay(mVideoDisplay.getHolder());
            mPlayer.prepare();
            mPlayer.start();

        } catch (Exception e) {
            Log.e(TAG, "prepare failed", e);
        }
    }

    private void stopMediaPlayer() {
        if (null != mPlayer) {
            mPlayer.stop();
        }
    }

    private void releaseMediaPlayer() {
        if (null != mPlayer) {
            mPlayer.release();
            mPlayer = null;
        }
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // implement the callback of SurfaceHolder.Callback2
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder holder) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surface created");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged");
        startMediaPlayer();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        stopMediaPlayer();
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // implement the callback of  MediaPlayer.xxListener
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "prepared");
        if (null != mPlayer) {
            mPlayer.start();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "completion");
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        Log.d(TAG, "[onBufferingUpdate(" + percent + ")]");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "onError( what: " + what + ", extra: " + extra + " )");
        stopMediaPlayer();
        return false;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // implement list
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    private void displayPopWindow() {
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_menu, null);

        View superView = getWindow().getDecorView();
        int with = (int) (superView.getWidth() * 0.85);
        int height = (int) (superView.getHeight() * 0.85);

        final PopupWindow popupWindow = new PopupWindow(contentView, with, height, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });

        ListView listView = contentView.findViewById(R.id.play_url_list);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(mItemClickListener);

        popupWindow.showAtLocation(superView, Gravity.CENTER, 0, 0);
    }


    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    // implement adapter
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private SimpleAdapter myAdapter = new SimpleAdapter();
    private ItemClickListener mItemClickListener = new ItemClickListener();

    private class SimpleAdapter extends BaseAdapter {

        private final int mNumber;

        SimpleAdapter() {
            mNumber = UrlManager.get().getUrls().size();
        }

        @Override
        public int getCount() {
            return mNumber;
        }

        @Override
        public UrlInfo getItem(int position) {
            if (position < 0 && position >= mNumber) {
                return null;
            }
            return UrlManager.get().getUrls().get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            if (null == view) {
                view = View.inflate(MainActivity.this, R.layout.pop_url_item_button, null);
            }

            UrlInfo info = getItem(position);
            if (null == info) {
                return null;
            }

            Button button = (Button) view;
            button.setText(info.getName());
            button.setTag(info);

            return button;
        }
    }

    private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
