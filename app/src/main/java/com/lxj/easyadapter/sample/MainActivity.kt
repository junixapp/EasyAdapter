package com.lxj.easyadapter.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.lxj.easyadapter.*

import kotlinx.android.synthetic.main.activity_main.*

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    internal var userList: MutableList<User> = ArrayList()
    private var adapter: EasyAdapter<User>? = null
    private var multiItemTypeAdapter: MultiItemTypeAdapter<*>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //prepare data
        for (i in 0..19) {
            userList.add(User("本杰明 - ", i * 2))
        }

        testHeader()
//        testMultiItem()
    }

    private fun testHeader() {
        adapter = object : EasyAdapter<User>(R.layout.item, userList) {
            override fun bind(holder: ViewHolder, user: User, position: Int) {
                with(holder) {
                    setText(R.id.tv_name, "name: " + user.name + position)
                    setText(R.id.tv_age, "age: " + user.age)
                }
            }
        }.apply {
            setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    super.onItemClick(view, holder, position)
                    Toast.makeText(this@MainActivity, "item - $position", Toast.LENGTH_SHORT).show()
                }
            })
            addHeaderView(createView("Header - 1"))
            addHeaderView(createView("Header - 2"))
            addFootView(createView("我是Footer - 1"))
            addFootView(createView("我是Footer - 2"))
            recyclerView.adapter = this
        }
    }

    private fun createView(text: String): TextView {
        val textView2 = TextView(this)
        textView2.setPadding(80, 80, 80, 80)
        textView2.text = text
        textView2.setOnClickListener {
            Toast.makeText(this@MainActivity, textView2.text.toString(), Toast.LENGTH_SHORT).show()
            userList.add(User("刘小姐", 33))
            if (multiItemTypeAdapter != null) multiItemTypeAdapter!!.notifyDataSetChanged()
            if (adapter != null) adapter!!.notifyDataSetChanged()
        }
        return textView2
    }

    internal fun testMultiItem() {
        multiItemTypeAdapter = MultiItemTypeAdapter(userList)
                .apply {
                    addItemViewDelegate(OneDelegate())
                    addItemViewDelegate(TwoDelegate())
                    addHeaderView(createView("Multi Header view1111"))
                    addHeaderView(createView("Multi Header view22222"))
                    addFootView(createView("Multi Footer view"))
                    setOnItemClickListener(object : MultiItemTypeAdapter.OnItemClickListener {
                        override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                            Toast.makeText(this@MainActivity, "position: $position", Toast.LENGTH_SHORT).show()
                        }

                        override fun onItemLongClick(view: View, holder: RecyclerView.ViewHolder, position: Int): Boolean {
                            return false
                        }
                    })
                    recyclerView.adapter = this
                }
    }

    internal inner class OneDelegate : ItemViewDelegate<User> {

        override val layoutId: Int
            get() = android.R.layout.simple_list_item_1

        override fun isForViewType(item: User, position: Int): Boolean {
            return position % 2 != 0
        }

        override fun bind(holder: ViewHolder, user: User, position: Int) {
            holder.setText(android.R.id.text1, "name: " + user.name + " - " + position)
        }
    }

    internal inner class TwoDelegate : ItemViewDelegate<User> {

        override val layoutId: Int
            get() = android.R.layout.simple_list_item_1

        override fun isForViewType(item: User, position: Int): Boolean {
            return position % 2 == 0
        }

        override fun bind(holder: ViewHolder, user: User, position: Int) {
            holder.setText(android.R.id.text1, "age: " + user.age)
            holder.getView<View>(android.R.id.text1).setBackgroundColor(Color.RED)
        }
    }
}
