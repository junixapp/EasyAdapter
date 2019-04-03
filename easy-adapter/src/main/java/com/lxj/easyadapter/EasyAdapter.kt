package com.lxj.easyadapter


/**
 * @param <T>
</T> */
abstract class EasyAdapter<T>(data: List<T>, protected var mLayoutId: Int) : MultiItemTypeAdapter<T>(data) {

    init {
        addItemDelegate(object : ItemDelegate<T> {
            override val layoutId: Int
                get() = mLayoutId

            override fun isThisType(item: T, position: Int): Boolean {
                return true
            }

            override fun bind(holder: ViewHolder, t: T, position: Int) {
                this@EasyAdapter.bind(holder, t, position)
            }
        })
    }

    protected abstract fun bind(holder: ViewHolder, t: T, position: Int)

}
