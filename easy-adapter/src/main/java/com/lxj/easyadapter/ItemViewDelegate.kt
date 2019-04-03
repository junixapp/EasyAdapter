package com.lxj.easyadapter

/**
 * Created by zhy on 16/6/22.
 */
interface ItemViewDelegate<T> {

    val layoutId: Int

    fun isForViewType(item: T, position: Int): Boolean

    fun bind(holder: ViewHolder, t: T, position: Int)

}
