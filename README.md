# EasyAdapter
An simplify version for hongyangAndroid [baseAdapter]. see https://github.com/hongyangAndroid/baseAdapter.

I remove some wrappers, change some api, to be more simple, support kotlin.

# Gradle
[ ![Download](https://api.bintray.com/packages/li-xiaojun/jrepo/easyadapter/images/download.svg) ](https://bintray.com/li-xiaojun/jrepo/easyadapter/_latestVersion)
```groovy
implementation 'com.lxj:easyadapter:latest release'
```

# Sample
普通使用：
```java
recyclerView.setAdapter(new CommonAdapter<User>(R.layout.item, userList) {
            @Override
            protected void convert(ViewHolder holder, User user, int position) {
                holder.setText(R.id.tv_name, "name: " + user.name)
                        .setText(R.id.tv_age, "age: " + user.age);
            }
        });
```

添加header和footer：
```java
commonAdapter.addHeaderView(textView);
commonAdapter.addHeaderView(textView2);
// 必须使用wrapper方法返回的adapter，否则无效
recyclerView.setAdapter(commonAdapter.wrapper());

// 使用commonAdapter的带有wrapper的更新方法
commonAdapter.notifyWrapperDataSetChanged();
```

条目点击：
```
commonAdapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener(){
    @Override
    public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
        super.onItemClick(view, holder, position);

    }
});
```

多条目：
```
MultiItemTypeAdapter adapter = new MultiItemTypeAdapter<User>(userList)
                .addItemViewDelegate(new OneDelegate())
                .addItemViewDelegate(...);
recyclerView.setAdapter(adapter);


class OneDelegate implements ItemViewDelegate<User> {
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public boolean isForViewType(@NonNull User item, int position) {
        return false;
    }

    @Override
    public void convert(@NonNull ViewHolder holder, @NonNull User user, int position) {

    }
}
```