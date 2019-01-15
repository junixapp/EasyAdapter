package com.lxj.easyadapter;


import android.support.annotation.NonNull;

/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getLayoutId();

    boolean isForViewType(@NonNull T item, int position);

    void convert(@NonNull ViewHolder holder, @NonNull T t, int position);

}
