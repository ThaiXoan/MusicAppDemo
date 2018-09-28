package it.dut.thaixoan.musicofnotification;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ThaiXoan on 27/09/2018.
 */

public class Constants {
    public interface ACTION {
        public static String MAIN_ACTION = "it.dut.thaixoan.musicofnotification.action.main";
        public static String INIT_ACTION = "it.dut.thaixoan.musicofnotification.action.init";
        public static String PREV_ACTION = "it.dut.thaixoan.musicofnotification.action.prev";
        public static String PLAY_ACTION = "it.dut.thaixoan.musicofnotification.action.play";
        public static String NEXT_ACTION = "it.dut.thaixoan.musicofnotification.action.next";
        public static String STARTFOREGROUND_ACTION = "it.dut.thaixoan.musicofnotification.action.startforeground";
        public static String STOPFOREGROUND_ACTION = "it.dut.thaixoan.musicofnotification.action.stopforeground";
    }

    public interface NOTIFICATION_ID {
        public static int FOREGROUND_SERVICE = 101;
    }

    public static Bitmap getDefaultImage(Context context) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        try {
            bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.music_image, options);
        } catch (Error error) {
        } catch (Exception ex) {
        }
        return bitmap;
    }
}
