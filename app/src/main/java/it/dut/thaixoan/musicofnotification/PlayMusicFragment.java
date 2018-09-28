package it.dut.thaixoan.musicofnotification;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.List;

public class PlayMusicFragment extends Fragment implements View.OnClickListener {
    private static final String BUNDLE_POSITION_SONG = "BUNDLE_POSITION_SONG";

    private ImageView mImageMusic;
    private TextView mTextMusicTitle;
    private TextView mTextMusicArtist;
    private int position;
    public List<Music> mMusicList;

    public static PlayMusicFragment newInstance(int position, List<Music> musicList) {
        Bundle args = new Bundle();
        PlayMusicFragment fragment = new PlayMusicFragment();
        fragment.mMusicList = musicList;
        args.putInt(BUNDLE_POSITION_SONG, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_play_music, container, false);

        Bundle bundle = getArguments();
        position = bundle.getInt(BUNDLE_POSITION_SONG);

        mImageMusic = view.findViewById(R.id.imageMusic);
        mTextMusicTitle = view.findViewById(R.id.textMusicTitle);
        mTextMusicArtist = view.findViewById(R.id.textMusicArtist);
        ImageButton imageButtonPrevious = view.findViewById(R.id.imageButtonPrevious);
        ImageButton imageButtonPlay = view.findViewById(R.id.imageButtonPlay);
        ImageButton imageButtonNext = view.findViewById(R.id.imageButtonNext);

        updateFragmentMusic(position);

        imageButtonPrevious.setOnClickListener(this);
        imageButtonPlay.setOnClickListener(this);
        imageButtonNext.setOnClickListener(this);
        mImageMusic.setOnClickListener(this);

        return view;
    }

    private void updateFragmentMusic(int position) {
        Glide.with(this)
                .load(mMusicList.get(position).getMusicImage())
                .placeholder(R.drawable.music_image)
                .into(mImageMusic);
        mTextMusicTitle.setText(mMusicList.get(position).getMusicTitle());
        mTextMusicArtist.setText(mMusicList.get(position).getMusicArtist());
    }

    @Override
    public void onClick(View v) {
        MainActivity mainActivity = (MainActivity) getActivity();
        switch (v.getId()) {
            case R.id.imageButtonNext:
                mainActivity.mMusicService.next(position);
                position++;
                if (position == mMusicList.size()) {
                    position = 0;
                }
                updateFragmentMusic(position);
                break;
            case R.id.imageButtonPlay:
                Toast.makeText(getActivity(),
                        "play nhac" + mMusicList.get(position).getMusicTitle(), Toast.LENGTH_LONG)
                        .show();
                break;

            case R.id.imageButtonPrevious:
                mainActivity.mMusicService.previous(position);
                if (position == 0) {
                    position = mMusicList.size();
                }
                position--;
                updateFragmentMusic(position);
                break;
            case R.id.imageMusic:
                Toast.makeText(getActivity(), "Click image", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }
    }
}
