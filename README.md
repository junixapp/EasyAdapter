# EasyAdapter
An simplify version for hongyangAndroid [baseAdapter]. see https://github.com/hongyangAndroid/baseAdapter.

I remove some wrappers, change some api, to be more simple.

# Gradle
```groovy
implementation 'com.lxj:easyadapter:0.0.1'
```

# Sample
```java
recyclerView.setAdapter(new CommonAdapter<User>(R.layout.item, userList) {
            @Override
            protected void convert(ViewHolder holder, User user, int position) {
                holder.setText(R.id.tv_name, "name: " + user.name)
                        .setText(R.id.tv_age, "age: " + user.age);
            }
        });
```

