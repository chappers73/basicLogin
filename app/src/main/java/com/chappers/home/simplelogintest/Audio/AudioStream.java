package com.chappers.home.simplelogintest.Audio;

//import com.aaronhan.rtspclient.RtspClinet.Stream.RtpStream;

import com.chappers.home.simplelogintest.Stream.RtpStream;

/**
 *
 */
public abstract class AudioStream extends RtpStream {
    private final static String tag = "AudioStream";

    protected void recombinePacket(StreamPacks streamPacks) {

    }
}
