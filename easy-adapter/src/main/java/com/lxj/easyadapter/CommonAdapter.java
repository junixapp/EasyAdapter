package com.lxj.easyadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;


/**
 * @param <T>
 */
public abstract class CommonAdapter<T> extends MultiItemTypeAdapter<T> {
    protected int mLayoutId;

    public CommonAdapter(final int layoutId, List<T> data) {
        super(data);
        mLayoutId = layoutId;
        mDatas = data;

        addItemViewDelegate(new ItemViewDelegate<T>() {
            @Override
            public int getLayoutId() {
                return layoutId;
            }

            @Override
            public boolean isForViewType(@NonNull T item, int position) {
                return true;
            }

            @Override
            public void bind(@NonNull ViewHolder holder, @NonNull T t, int position) {
                CommonAdapter.this.bind(holder, t, position);
            }
        });
    }

    protected abstract void bind(@NonNull ViewHolder holder, @NonNull T t, int position);

}
