package it.dut.thaixoan.musicofnotification;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.util.List;

/**
 * Created by ThaiXoan on 26/09/2018.
 */

public class MusicService extends Service implements MediaPlayer.OnPreparedListener {

    private List<Music> mMusicList;
    private MediaPlayer mMediaPlayer;
    private IBinder mIBinder = new LocalBinder();

    public MusicService() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        mMusicList = (List<Music>) intent.getSerializableExtra("list");
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public boolean checkPlayOrPause(){
        if (mMediaPlayer.isPlaying()){

        }
        return true;
    }

    public void play(int position) {

        String path = mMusicList.get(position).getData();
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    public void next(int position) {
        if (position == mMusicList.size() - 1) {
            position = -1;
        }
        position++;
        play(position);
    }

    public void previous(int position) {
        if (position == 0) {
            position = mMusicList.size();
        }
        position--;
        play(position);
    }

    public class LocalBinder extends Binder {
        public MusicService getLMusicService() {
            return MusicService.this;
        }
    }
}
