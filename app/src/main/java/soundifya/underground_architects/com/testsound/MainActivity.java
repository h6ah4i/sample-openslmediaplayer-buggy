package soundifya.underground_architects.com.testsound;

import android.database.Cursor;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.h6ah4i.android.media.IBasicMediaPlayer;
import com.h6ah4i.android.media.IMediaPlayerFactory;
import com.h6ah4i.android.media.hybrid.HybridMediaPlayerFactory;
import com.h6ah4i.android.media.opensl.OpenSLMediaPlayerFactory;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
static ArrayList<AudioModel> audioData;
    double totaltime;
    public static IBasicMediaPlayer mediaPlayer;
    public static boolean isPreparing= false;
    public static AudioModel nowAudioModel;
    public static IMediaPlayerFactory factory;
    public static int nowIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createMediaPlayer();
    }

    private void createMediaPlayer() {
        if(factory==null) {
            factory = new HybridMediaPlayerFactory(getApplicationContext());
//            factory = new OpenSLMediaPlayerFactory(getApplicationContext());
        }
        mediaPlayer = factory.createMediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new IBasicMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IBasicMediaPlayer mp) {
                isPreparing = false;
                Log.d("XXX", "[" + nowIndex + "] onPrepared()");
                mediaPlayer.start();
            }
        });
        mediaPlayer.setOnCompletionListener(new IBasicMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IBasicMediaPlayer mp) {
                long delay = (new Random()).nextInt(2000);

                Log.d("XXX", "[" + nowIndex + "] onCompletion(); delay = " + delay);
                getWindow().getDecorView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        nextSong(null);
                    }
                }, delay);
            }
        });
        mediaPlayer.setOnErrorListener(new IBasicMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IBasicMediaPlayer mp, int what, int extra) {
                Log.d("XXX", "[" + nowIndex + "] onError(what = " + what + ", extra = " + extra + ")");
                return false;
            }
        });
        mediaPlayer.setOnInfoListener(new IBasicMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IBasicMediaPlayer mp, int what, int extra) {
                Log.d("XXX", "[" + nowIndex + "] onInfo(what = " + what + ", extra = " + extra + ")");
                return false;
            }
        });

        handleAudioData();
    }

    public void handleAudioData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final double currentTime = System.currentTimeMillis();
                final Uri audio_uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                final String[] audio_projection = {MediaStore.Audio.Media._ID,
                        MediaStore.Audio.Media.ARTIST, MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.ARTIST_ID,
                        MediaStore.Audio.Media.COMPOSER,
                        MediaStore.Audio.Media.TRACK,
                        MediaStore.Audio.Media.YEAR,
                        MediaStore.Audio.Media.DISPLAY_NAME,
                        MediaStore.Audio.Media.DURATION
                };
                final String audio_where = MediaStore.Audio.Media.IS_MUSIC + "=1";
                Cursor cursor = getContentResolver().query(audio_uri,audio_projection,audio_where,null,MediaStore.Audio.Media.TITLE + " COLLATE NOCASE ASC");
                final ArrayList<AudioModel> audioarray = new ArrayList<>();
                Log.i("Soundifya", "fetching audio home");
                try {
                    while (cursor != null && cursor.moveToNext()) {
                        String artist = cursor.getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                        String album = cursor.getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                        String title = cursor.getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                        String data = cursor.getString(cursor
                                .getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                        if (data != null) {
                            if (data.endsWith("brown_noise_5sec.wav")) {
                                for (int i = 0; i < 100; i++) {
                                    long albumId = cursor.getLong(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));
                                    long duration = cursor.getLong(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                                    String year = String.valueOf(cursor.getInt(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.YEAR)));
                                    String tracknum = String.valueOf(cursor.getInt(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.TRACK)));
                                    String displayname = cursor.getString(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
                                    String composer = cursor.getString(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.COMPOSER));
                                    long artistId = cursor.getLong(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST_ID));
                                    long trackID = cursor.getLong(cursor
                                            .getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                                    if (artist == null || artist.equalsIgnoreCase("") || artist.equalsIgnoreCase("<unknown>")) {
                                        artist = "Unknown Artist";
                                    }
                                    if (album == null || album.equalsIgnoreCase("") || album.equalsIgnoreCase("<unknown>")) {
                                        album = "Unknown Album";
                                    }
                                    AudioModel audioModel = new AudioModel();
                                    audioModel.setArtist(artist);
                                    audioModel.setAlbum(album);
                                    audioModel.setTitle(title);
                                    audioModel.setData(data);
                                    audioModel.setDuration(duration);
                                    audioModel.setYear(year);
                                    if (tracknum.length() == 4) {
                                        audioModel.setTracknum(String.valueOf(Integer.parseInt(tracknum.substring(1, 4))));
                                        audioModel.setDisknum(tracknum.substring(0, 1));
                                    } else if (tracknum.length() < 4) {
                                        audioModel.setTracknum(String.valueOf(Integer.parseInt(tracknum)));
                                    }
                                    audioModel.setDisplayname(displayname);
                                    audioModel.setComposer(composer);
                                    audioModel.setAlbumId(albumId);
                                    audioModel.setTrackId(trackID);
                                    audioModel.setArtistId(artistId);
                                    audioModel.setTrackIndex(audioarray.size());

                                    audioarray.add(audioModel);
                                }
                            }
                        }
                    }
                }
                finally {
                    if (cursor != null)
                        cursor.close();
                }


                getWindow().getDecorView().post(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.audioData = audioarray;
                        MainActivity.nowAudioModel = MainActivity.audioData.get(nowIndex);
                        ((TextView) findViewById(R.id.name)).setText(MainActivity.nowAudioModel.getTitle());
                        ((TextView) findViewById(R.id.data)).setText(MainActivity.nowAudioModel.getData());
                        PlayerControls.changeSong(getApplicationContext());
                        double trackTime = (System.currentTimeMillis() - currentTime)/1000;
                        totaltime = totaltime + trackTime;
                        System.out.println("Track time "+trackTime);
                        Log.i("Soundifya", "Total Tracks :" + audioarray.size());
                    }
                });
            }
        }).start();
    }

    public void nextSong(View view)
    {
        nowIndex++;
        if(nowIndex>=0 && nowIndex<MainActivity.audioData.size()) {
            MainActivity.nowAudioModel = MainActivity.audioData.get(nowIndex);
            ((TextView) findViewById(R.id.name)).setText(MainActivity.nowAudioModel.getTitle());
            ((TextView) findViewById(R.id.data)).setText(MainActivity.nowAudioModel.getData());
            PlayerControls.changeSong(getApplicationContext());
        }
        else
        {
            nowIndex--;
        }
    }

    public void previousSong(View view)
    {
        nowIndex--;
        if(nowIndex>=0 && nowIndex<MainActivity.audioData.size()) {
            MainActivity.nowAudioModel = MainActivity.audioData.get(nowIndex);
            ((TextView) findViewById(R.id.name)).setText(MainActivity.nowAudioModel.getTitle());
            ((TextView) findViewById(R.id.data)).setText(MainActivity.nowAudioModel.getData());
            PlayerControls.changeSong(getApplicationContext());
        }
        else
        {
            nowIndex++;
        }
    }

    public void playPause(View view)
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
            ((ImageView)findViewById(R.id.playpause)).setImageResource(R.drawable.ic_play_arrow_black_24dp);
        }
        else
        {
            mediaPlayer.start();
            ((ImageView)findViewById(R.id.playpause)).setImageResource(R.drawable.ic_pause_black_24dp);
        }
    }

}
