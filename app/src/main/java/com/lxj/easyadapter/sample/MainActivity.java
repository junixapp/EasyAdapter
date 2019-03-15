package com.lxj.easyadapter.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lxj.easyadapter.CommonAdapter;
import com.lxj.easyadapter.ItemViewDelegate;
import com.lxj.easyadapter.MultiItemTypeAdapter;
import com.lxj.easyadapter.ViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<User> userList = new ArrayList<>();
    private CommonAdapter<User> adapter;
    private MultiItemTypeAdapter multiItemTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //prepare data
        for (int i = 0; i < 20; i++) {
            userList.add(new User("本杰明 - ", i * 2));
        }

        testHeader();
//        testMultiItem();
    }

    void testHeader() {
        adapter = new CommonAdapter<User>(R.layout.item, userList) {
            @Override
            protected void bind(ViewHolder holder, User user, int position) {
                holder.setText(R.id.tv_name, "name: " + user.name + position)
                        .setText(R.id.tv_age, "age: " + user.age);
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                super.onItemClick(view, holder, position);
                Toast.makeText(MainActivity.this, "item - " + position, Toast.LENGTH_SHORT).show();
            }
        });

        adapter.addHeaderView(createView("Header - 1"));
        adapter.addHeaderView(createView("Header - 2"));
        adapter.addFootView(createView("我是Footer - 1"));
        adapter.addFootView(createView("我是Footer - 2"));
        recyclerView.setAdapter(adapter);
    }

    TextView createView(String text) {
        final TextView textView2 = new TextView(this);
        textView2.setPadding(80, 80, 80, 80);
        textView2.setText(text);
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, textView2.getText().toString(), Toast.LENGTH_SHORT).show();
                userList.add( new User("刘小姐", 33));
                if(multiItemTypeAdapter!=null)multiItemTypeAdapter.notifyDataSetChanged();
                if(adapter!=null)adapter.notifyDataSetChanged();
            }
        });
        return textView2;
    }

    void testMultiItem() {

        multiItemTypeAdapter = new MultiItemTypeAdapter<User>(userList)
                .addItemViewDelegate(new OneDelegate())
                .addItemViewDelegate(new TwoDelegate());
        multiItemTypeAdapter.addHeaderView(createView("Multi Header view1111"));
        multiItemTypeAdapter.addHeaderView(createView("Multi Header view22222"));
        multiItemTypeAdapter.addFootView(createView("Multi Footer view"));
        multiItemTypeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, @NonNull RecyclerView.ViewHolder holder, int position) {
                Toast.makeText(MainActivity.this, "position: " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(@NonNull View view, @NonNull RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recyclerView.setAdapter(multiItemTypeAdapter);
    }

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
}
