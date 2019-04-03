package com.lxj.easyadapter


/**
 * @param <T>
</T> */
abstract class EasyAdapter<T>(protected var mLayoutId: Int, data: List<T>) : MultiItemTypeAdapter<T>(data) {

    init {
        addItemViewDelegate(object : ItemViewDelegate<T> {
            override val layoutId: Int
                get() = mLayoutId

            override fun isForViewType(item: T, position: Int): Boolean {
                return true
            }

            override fun bind(holder: ViewHolder, t: T, position: Int) {
                this@EasyAdapter.bind(holder, t, position)
            }
        })
    }

    protected abstract fun bind(holder: ViewHolder, t: T, position: Int)

}
