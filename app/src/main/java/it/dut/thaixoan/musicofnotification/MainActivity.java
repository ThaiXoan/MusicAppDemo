package it.dut.thaixoan.musicofnotification;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ViewGroup;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnItemRecyclerViewClickListener {

    private RecyclerView mRecyclerView;
    private List<Music> mMusicList;
    private MusicAdapter mMusicAdapter;

    public MusicService mMusicService;

    android.support.v7.app.ActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPermission();
        addControls();
        addEvents();
    }

    @Override
    protected void onStart() {
        super.onStart();
        StartBoundService();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }, 1);
            }
        }
    }

    private void addControls() {
        mActionBar = getSupportActionBar();
        mActionBar.setTitle("My Music");

        mRecyclerView = findViewById(R.id.recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mMusicList = new ArrayList<>();
        mMusicAdapter = new MusicAdapter(this, mMusicList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMusicAdapter);
        mMusicAdapter.setItemRecyclerViewClick(this);
    }

    private void addEvents() {
        loadMusicFromStorage();
    }

    private void loadMusicFromStorage() {
        ContentResolver contentResolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor cursor = contentResolver.query(uri, null, null, null, sortOrder);
        if (cursor != null && cursor.getCount() > 0) {
            Music music;
            while (cursor.moveToNext()) {
                String data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                String title =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album =
                        cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String image = String.valueOf(ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"), albumId));

                music = new Music(data, image, title, artist, album);
                mMusicList.add(music);
            }
            mMusicAdapter.notifyDataSetChanged();
        }
        cursor.close();
    }

    @Override
    public void onItemClick(int position, List list) {
        if (mMusicService != null) {
            onNewFragment(PlayMusicFragment.newInstance(position, list));
            mMusicService.play(position);
        }
    }

    private void StartBoundService() {
        Intent mIntent = new Intent(MainActivity.this, MusicService.class);
        mIntent.putExtra("list", (Serializable) mMusicList);
        bindService(mIntent, mServiceConnection, BIND_AUTO_CREATE);
//        Intent intent = new Intent(MainActivity.this, MusicService.class);
//        startService(intent);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) iBinder;
            mMusicService = binder.getLMusicService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void onNewFragment(Fragment fragment) {
        ViewGroup.LayoutParams params = mRecyclerView.getLayoutParams();
        params.height = 600;
        mRecyclerView.setLayoutParams(params);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.add(android.R.id.content, fragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }
}
