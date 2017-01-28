package soundifya.underground_architects.com.testsound;

import android.content.Context;
import android.net.Uri;

import com.h6ah4i.android.media.IBasicMediaPlayer;

import java.io.IOException;

/**
 * Created by HuNny on 14 Mar 2016.
 */
public class PlayerControls {

    public static void changeSong(final Context context)
    {
        playerControlMains(context);
        }


    public static void prepareData(final Context context)
    {
        MainActivity.mediaPlayer.setOnPreparedListener(new IBasicMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IBasicMediaPlayer mp) {
                MainActivity.isPreparing = false;
                MainActivity.mediaPlayer.start();
            }
        });
        try {
            if(!MainActivity.isPreparing) {
                MainActivity.isPreparing = true;
                MainActivity.mediaPlayer.prepareAsync();
            }
            else
            {
                while (MainActivity.isPreparing)
                {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                MainActivity.isPreparing = true;
                MainActivity.mediaPlayer.prepareAsync();
            }
        } catch (IllegalStateException e) {
            System.err.println("illegal state pp repairing");
            try {
                Thread.sleep(3500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            prePrepare(context);
        }
    }

    public static void prePrepare(Context context) {
        if(MainActivity.mediaPlayer.isPlaying()) {
            MainActivity.mediaPlayer.pause();
        }
        MainActivity.mediaPlayer.reset();
        try {
            MainActivity.mediaPlayer.setDataSource(context, Uri.parse("file://" + MainActivity.nowAudioModel.getData()));
            MainActivity.isPreparing = false;
            prepareData(context);
        } catch (IllegalStateException|IOException e) {
            System.err.println("illegal state ds repairing");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            prePrepare(context);
        }
    }

    public static void playerControlMains(final Context context)
    {
        prePrepare(context);
    }

}
