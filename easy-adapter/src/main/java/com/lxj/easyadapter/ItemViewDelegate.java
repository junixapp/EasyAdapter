package com.lxj.easyadapter;


/**
 * Created by zhy on 16/6/22.
 */
public interface ItemViewDelegate<T>
{

    int getLayoutId();

    boolean isForViewType(T item, int position);

    void convert(ViewHolder holder, T t, int position);

}
