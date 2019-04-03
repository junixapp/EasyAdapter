package com.lxj.easyadapter.sample

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.lxj.easyadapter.*

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class MainActivity : AppCompatActivity() {

    internal var userList: MutableList<User> = ArrayList()
    private var adapter: EasyAdapter<User>? = null
    private var multiItemTypeAdapter: MultiItemTypeAdapter<User>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView.layoutManager = LinearLayoutManager(this)

        //prepare data
        for (i in 0..6) {
            userList.add(User("本杰明 - $i", i * 2, i))
        }

        testHeader()
//        testMultiItem()
    }

    private fun testHeader() {
        adapter = object : EasyAdapter<User>(userList, R.layout.item) {
            override fun bind(holder: ViewHolder, user: User, position: Int) {
                with(holder) {
                    setText(R.id.tv_name, "name: " + user.name )
                    setText(R.id.tv_age, "age: " + user.age)
                }
            }
        }.apply {
            setOnItemClickListener(object : MultiItemTypeAdapter.SimpleOnItemClickListener() {
                override fun onItemClick(view: View, holder: RecyclerView.ViewHolder, position: Int) {
                    super.onItemClick(view, holder, position)
                    Toast.makeText(this@MainActivity, "position - $position", Toast.LENGTH_SHORT).show()
                    userList.removeAt(position)
//                    notifyDataSetChanged()
                    notifyItemRemoved(position + headersCount)
                }
            })
            addHeaderView(createView("Header - 1，点我在头部添加一条数据"))
            addHeaderView(createView("Header - 2，点我在头部添加一条数据"))
            addFootView(createView("Footer - 1，点我在末尾添加一条数据", true))
            addFootView(createView("Footer - 2，点我在末尾添加一条数据", true))
            recyclerView.adapter = this
        }
    }

    private fun createView(text: String, isFooter: Boolean = false): TextView {
        val textView = TextView(this)
        textView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView.setPadding(80, 80, 80, 80)
        textView.text = text
        textView.setOnClickListener {
            Toast.makeText(this@MainActivity, textView.text.toString(), Toast.LENGTH_SHORT).show()
            val user = User()
            user.name = "随机添加的名字 - ${System.currentTimeMillis()}"
            user.age = System.currentTimeMillis().toInt()
            if(isFooter){
                //尾部添加
                userList.add(user)
            }else{
                userList.add(0, user)
            }
            multiItemTypeAdapter?.apply { notifyItemInserted(if (isFooter) (userList.size + headersCount) else headersCount) }
            adapter?.apply { notifyItemInserted(if (isFooter) (userList.size + headersCount) else headersCount) }
        }
        return textView
    }

    internal fun testMultiItem() {
        multiItemTypeAdapter = MultiItemTypeAdapter<User>(userList)
                .apply {
                    addItemDelegate(OneDelegate())
                    addItemDelegate(TwoDelegate())
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

    internal inner class OneDelegate : ItemDelegate<User> {
        override val layoutId: Int
            get() = android.R.layout.simple_list_item_1

        override fun isThisType(item: User, position: Int): Boolean {
            return position % 2 != 0
        }
        override fun bind(holder: ViewHolder, user: User, position: Int) {
            holder.setText(android.R.id.text1, "name: " + user.name + " - " + position)
        }
    }

    internal inner class TwoDelegate : ItemDelegate<User> {
        override val layoutId: Int
            get() = android.R.layout.simple_list_item_1

        override fun isThisType(item: User, position: Int): Boolean {
            return position % 2 == 0
        }
        override fun bind(holder: ViewHolder, user: User, position: Int) {
            holder.setText(android.R.id.text1, "age: " + user.age)
            holder.getView<View>(android.R.id.text1).setBackgroundColor(Color.RED)
        }
    }
}
