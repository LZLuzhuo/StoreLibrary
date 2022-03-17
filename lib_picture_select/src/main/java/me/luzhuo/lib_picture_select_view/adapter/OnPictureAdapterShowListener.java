package me.luzhuo.lib_picture_select_view.adapter;

import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public interface OnPictureAdapterShowListener {
    @RestrictTo(LIBRARY)
    public void onPictureAdapterShow(int position);
}
