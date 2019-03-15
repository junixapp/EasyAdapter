# EasyAdapter
An simplify and powerful version for hongyangAndroid [baseAdapter].

I remove some class, change some api, rewrite code, to be more simple, support kotlin.

# Gradle
[ ![Download](https://api.bintray.com/packages/li-xiaojun/jrepo/easyadapter/images/download.svg) ](https://bintray.com/li-xiaojun/jrepo/easyadapter/_latestVersion)
```groovy
implementation 'com.lxj:easyadapter:latest release'
```

# Sample
普通使用：
```java
adapter = new CommonAdapter<User>(R.layout.item, userList) {
            @Override
            protected void bind(ViewHolder holder, User user, int position) {
                //传入的位置是不包含header在内的
                holder.setText(R.id.tv_name, "name: " + user.name + position)
                        .setText(R.id.tv_age, "age: " + user.age);
            }
        };
adapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        super.onItemClick(view, holder, position);
        //传入的位置是不包含header在内的
        Toast.makeText(MainActivity.this, "item - " + position, Toast.LENGTH_SHORT).show();
    }
});

adapter.addHeaderView(createView("Header - 1"));
adapter.addHeaderView(createView("Header - 2"));
adapter.addFootView(createView("我是Footer - 1"));
adapter.addFootView(createView("我是Footer - 2"));
recyclerView.setAdapter(adapter);
```


多条目：
```java
multiItemTypeAdapter = new MultiItemTypeAdapter<User>(userList)
                .addItemViewDelegate(new OneDelegate())
                .addItemViewDelegate(new TwoDelegate());
multiItemTypeAdapter.addHeaderView(createView("Multi Header view1111"));
multiItemTypeAdapter.addHeaderView(createView("Multi Header view22222"));
multiItemTypeAdapter.addFootView(createView("Multi Footer view"));
multiItemTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
    @Override
    public void onItemClick(@NonNull View view, @NonNull RecyclerView.ViewHolder holder, int position) {
        //传入的位置是不包含header在内的
        Toast.makeText(MainActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onItemLongClick(@NonNull View view, @NonNull RecyclerView.ViewHolder holder, int position) {
        //传入的位置是不包含header在内的
        return false;
    }
});
recyclerView.setAdapter(multiItemTypeAdapter);

class OneDelegate implements ItemViewDelegate<User> {
    @Override
    public int getLayoutId() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public boolean isForViewType(@NonNull User item, int position) {
        return position % 2 != 0;
    }

    @Override
    public void bind(@NonNull ViewHolder holder, @NonNull User user, int position) {
        holder.setText(android.R.id.text1, "name: " + user.name + " - "+ position);
    }
}

class TwoDelegate implements ItemViewDelegate<User> {
    @Override
    public int getLayoutId() {
        return android.R.layout.simple_list_item_1;
    }

    @Override
    public boolean isForViewType(@NonNull User item, int position) {
        return position % 2 == 0;
    }

    @Override
    public void bind(@NonNull ViewHolder holder, @NonNull User user, int position) {
        holder.setText(android.R.id.text1, "age: " + user.age)
                .getView(android.R.id.text1).setBackgroundColor(Color.RED);
    }
}
```