package com.chappers.home.simplelogintest;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;

import com.chappers.home.simplelogintest.Video.H264Stream;

//import com.chappers.home.simplelogintest.Video.H264Stream;

public class CameraTest extends AppCompatActivity implements  SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;
    private SurfaceHolder surfaceHolder;
    private RtspClient mRtspClient;
    RtspClient.SDPInfo obj;
    private static final String TAG = "CameraTest";
    private int screenWidth;
    private int screenHeight;
    //private mRtspClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mSurfaceView = (SurfaceView)findViewById(R.id.sv2_cam);

        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.setSizeFromLayout();

        //mSurfaceView.setZOrderMediaOverlay(true);
        //mSurfaceView.setZOrderMediaOverlay(true);
        //mSurfaceView.setZOrderOnTop(true);
        //mSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.addCallback(this);

        String host = "rtsp://192.168.1.198/11";
        //RtspClient(host,username,password,port);
        mRtspClient = new RtspClient("tcp",host);

        mSurfaceView.setVisibility(View.VISIBLE);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated: Starting...");

        //mSurfaceView.setVisibility(View.GONE);

        WindowManager manager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        screenWidth = display.getWidth();
        screenHeight = display.getHeight();
        //int width = screenWidth >= screenHeight ? screenWidth : screenHeight;
        //int height = screenWidth + screenHeight - width;
        int rotation = display.getRotation();
        Log.i(TAG, "surfaceCreated: Screen Rotation - " + Integer.toString(rotation));

        switch (rotation){
            case 0:
            case 2:
                mSurfaceView.setRotation(90);
                break;

            case 1:
            case 3:
                mSurfaceView.setRotation(0);
                break;


        }

        //mSurfaceView.setRotation(90);
        mRtspClient.setSurfaceView(mSurfaceView);


        mRtspClient.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        int[] vals = new int[2];
        vals = mRtspClient.mH264Stream.getPicInfo();
        Log.i(TAG, "surfaceCreated: H264 ImageInfo, Width - " + Integer.toString(vals[0]) + ", Height - " + Integer.toString(vals[1]));



        //Log.i(TAG, "surfaceCreated: ScreenWidth - " + Integer.toString(screenWidth));
        //Log.i(TAG, "surfaceCreated: ScreenHeight - " + Integer.toString(screenHeight));
        //mSurfaceView.getHolder().setFixedSize(height, width);
        mSurfaceView.setVisibility(View.VISIBLE);

        if (mRtspClient.isStarted())
            Log.i(TAG, "onClick: mRtspClient Started");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //Log.i(TAG, "surfaceChanged: Width - " + Integer.toString(width) + ", Height - " + Integer.toString(height));


    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed: Shutting down Client");
        mRtspClient.shutdown();

    }
}
