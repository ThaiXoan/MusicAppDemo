package it.dut.thaixoan.musicofnotification;

import java.io.Serializable;

/**
 * Created by ThaiXoan on 26/09/2018.
 */

public class Music implements Serializable {
    private String mData;
    private String mMusicImage;
    private String mMusicTitle;
    private String mMusicArtist;
    private String mMusicAlbum;

    public Music(String data, String musicImage, String musicTitle, String musicArtist,
            String musicAlbum) {
        mData = data;
        mMusicImage = musicImage;
        mMusicTitle = musicTitle;
        mMusicArtist = musicArtist;
        mMusicAlbum = musicAlbum;
    }

    public String getData() {
        return mData;
    }

    public void setData(String data) {
        mData = data;
    }

    public String getMusicImage() {
        return mMusicImage;
    }

    public void setMusicImage(String musicImage) {
        mMusicImage = musicImage;
    }

    public String getMusicTitle() {
        return mMusicTitle;
    }

    public void setMusicTitle(String musicTitle) {
        mMusicTitle = musicTitle;
    }

    public String getMusicArtist() {
        return mMusicArtist;
    }

    public void setMusicArtist(String musicArtist) {
        mMusicArtist = musicArtist;
    }

    public String getMusicAlbum() {
        return mMusicAlbum;
    }

    public void setMusicAlbum(String musicAlbum) {
        mMusicAlbum = musicAlbum;
    }
}
