package me.luzhuo.lib_picture_select_view.adapter;

import androidx.annotation.RestrictTo;

import static androidx.annotation.RestrictTo.Scope.LIBRARY;

@RestrictTo(LIBRARY)
public interface OnPictureAdapterSelectListener extends OnPictureAdapterShowListener {
    @RestrictTo(LIBRARY)
    public void onPictureAdapterSelect();
    @RestrictTo(LIBRARY)
    public void onPictureAdapterDelete(int position);
}
