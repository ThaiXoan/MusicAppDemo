package it.dut.thaixoan.musicofnotification;

import java.util.List;

/**
 * Created by ThaiXoan on 27/09/2018.
 */

public interface OnItemRecyclerViewClickListener<T> {
    void onItemClick(int position, List<T> list);// generics
}
