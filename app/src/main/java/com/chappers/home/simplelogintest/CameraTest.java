package com.chappers.home.simplelogintest;

import android.graphics.PixelFormat;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.chappers.home.simplelogintest.Video.H264Stream;

//import com.chappers.home.simplelogintest.Video.H264Stream;

public class CameraTest extends AppCompatActivity implements  SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;
    private SurfaceHolder surfaceHolder;
    private RtspClient mRtspClient;
    RtspClient.SDPInfo obj;
    private static final String TAG = "CameraTest";
    //private mRtspClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        mSurfaceView = (SurfaceView)findViewById(R.id.sv2_cam);

        surfaceHolder = mSurfaceView.getHolder();

        //mSurfaceView.setZOrderMediaOverlay(true);
        //mSurfaceView.setZOrderMediaOverlay(true);
        //mSurfaceView.setZOrderOnTop(true);
        //mSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceHolder.addCallback(this);

        String host = "rtsp://192.168.1.198/11";
        //RtspClient(host,username,password,port);
        mRtspClient = new RtspClient("tcp",host);




        //Outer.Inner obj = (new Outer).new Inner();

        //Outer.Inner obj = new Outer().new Inner();
        //RtspClient.SDPInfo obj = new RtspClient().new SDPInfo();
        //Log.i(TAG, "onCreate: videoTrack" + obj.videoTrack);




//Outerclass.InnerClass innerObject = outerObject.new Innerclass();

    }
    //public class Outer {
    //
    //    public class Inner { }
    //
    //    public static class StaticNested { }
    //
    //    public void method () {
    //        // non-static methods can instantiate static and non-static nested classes
    //        Inner i = new Inner(); // 'this' is the implied enclosing instance
    //        StaticNested s = new StaticNested();
    //    }
    //
    //    public static void staticMethod () {
    //        Inner i = new Inner(); // <-- ERROR! there's no enclosing instance, so cant do this
    //        StaticNested s = new StaticNested(); // ok: no enclosing instance needed
    //
    //        // but we can create an Inner if we have an Outer:
    //        Outer o = new Outer();
    //        Inner oi = o.new Inner(); // ok: 'o' is the enclosing instance
    //    }
    //
    //}

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.i(TAG, "surfaceCreated: Starting...");
        mRtspClient.start();

        mRtspClient.setSurfaceView(mSurfaceView);


        //RtspClient.SDPInfo rp = mRtspClient.new SDPInfo();

        //Log.i(TAG, "onCreate: " +rp.toString());

        //Outer.Inner obj = (new Outer).new Inner();

        //Outer.Inner obj = new Outer().new Inner();
        //RtspClient.SDPInfo obj = RtspClient.new RtspClient.SDPInfo();
        //Log.i(TAG, "surfaceCreated: - videoTrack" + obj.videoTrack);


        //mRtspClient.getClass(RtspClient.Response).
        //RtspClient.Response.
        //mSurfaceView.getHolder().setFixedSize();
        //mSurfaceView.setZOrderMediaOverlay(true);
        //mSurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);


        //mSurfaceView.setZOrderMediaOverlay(true);
        //mSurfaceView.setZOrderOnTop(true);

        //mRtspClient.start();

        if (mRtspClient.isStarted())
            Log.i(TAG, "onClick: mRtspClient Started");

        RtspClient.SDPInfo oi = mRtspClient.new SDPInfo();
        Log.i(TAG, "onCreate: " + oi.videoTrack);
        //mRtspClient.SDPInfo()
        obj = mRtspClient.new SDPInfo();

        Log.i(TAG, "surfaceCreated: - videoTrack" + obj.videoTrack);



    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i(TAG, "surfaceDestroyed: Shutting down Client");
        mRtspClient.shutdown();

    }
}
