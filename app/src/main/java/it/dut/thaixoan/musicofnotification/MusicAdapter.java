package it.dut.thaixoan.musicofnotification;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import java.util.List;

/**
 * Created by ThaiXoan on 26/09/2018.
 */

public class MusicAdapter extends RecyclerView.Adapter<MusicAdapter.MusicViewHolder> {

    private Context mContext;
    private List<Music> mMusicList;
    private OnItemRecyclerViewClickListener<Music> mItemRecyclerViewClick;

    MusicAdapter(Context context, List<Music> musicList) {
        mContext = context;
        mMusicList = musicList;
    }

    public void setItemRecyclerViewClick(OnItemRecyclerViewClickListener<Music> itemRecyclerViewClick) {
        mItemRecyclerViewClick = itemRecyclerViewClick;
    }

    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.music_item, parent, false);
        return new MusicViewHolder(mContext, view, mItemRecyclerViewClick);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, final int position) {
        holder.bindDataView(mMusicList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMusicList != null ? mMusicList.size() : 0;
    }

    class MusicViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Context mContext;
        private ImageView mMusicImage;
        private TextView mMusicTitle;
        private TextView mMusicArtist;
        private TextView mMusicAlbum;
        private OnItemRecyclerViewClickListener<Music> mListener;

        MusicViewHolder(final Context context, View itemView,
                OnItemRecyclerViewClickListener<Music> onItemRecyclerViewClickListener) {
            super(itemView);
            mContext = context;
            mListener = onItemRecyclerViewClickListener;
            mMusicImage = itemView.findViewById(R.id.imageMusic);
            mMusicTitle = itemView.findViewById(R.id.textMusicTitle);
            mMusicArtist = itemView.findViewById(R.id.textMusicArtist);
            mMusicAlbum = itemView.findViewById(R.id.textMusicAlbum);

            itemView.setOnClickListener(this);
        }

        void bindDataView(Music music) {
            mMusicTitle.setText(music.getMusicTitle());
            mMusicAlbum.setText(music.getMusicAlbum());
            mMusicArtist.setText(music.getMusicArtist());

            setImageSong(music.getMusicImage());
        }

        private void setImageSong(String musicImage) {
            Glide.with(mContext)
                    .load(musicImage)
                    .placeholder(R.drawable.song_image)
                    .into(mMusicImage);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(getAdapterPosition(), mMusicList);
            }
        }
    }
}
