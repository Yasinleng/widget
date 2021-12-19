package com.yasin.widget.recycler

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import com.yasin.view.recyclerview.DividerItemDecoration
import com.yasin.widget.FeatureAdapter
import com.yasin.widget.R
import com.yasin.widget.utils.PollThread
import kotlin.math.log

class RecyclerActivity : AppCompatActivity() {

    var list: MutableList<User> = mutableListOf()
    private lateinit var countTv: TextView
    private lateinit var add: TextView
    private lateinit var rv: RecyclerView


    private var adapter: UserAdapter? = null

    private var count = 0

    private var refresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        initView()
        recycler()


    }

    private fun initView() {
        countTv = findViewById(R.id.count)
        add = findViewById(R.id.add)
        rv = findViewById(R.id.user_rv)


        add.setOnClickListener {


            adapter?.setData(createData(5))
            refresh = true

            countTv.text = "${list.size}"
        }

    }

    fun recycler() {

        //        1. 线性布局
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        rv.setLayoutManager(linearLayoutManager)
        val dividerItemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration.Builder(this)
            .orientation(LinearLayoutManager.VERTICAL)
            .dividerColor(R.color.black)
            .build()
        rv.addItemDecoration(dividerItemDecoration)

        adapter = UserAdapter(this)
        adapter?.setData(createData())
        adapter?.notifyDataSetChanged()

        rv.adapter = adapter


        rv.addOnScrollListener(RecyclerScrollListener())


        val tast = PollThread();

        tast.start(object : PollThread.Action {
            override fun start() {

                createData(5)

                runOnUiThread {
                    adapter?.setData(list)
                    adapter?.notifyDataSetChanged()
                }

                refresh = true
            }
        })


    }


    private fun createData(num: Int = 100000): MutableList<User> {

        val temp = mutableListOf<User>()
        for (i in 0..num) {
            val user = User("头像", "姓名$count", "内容$count")
            count++
            list.add(user)
        }

        list.reverse()
        return list
    }

    inner class RecyclerScrollListener : RecyclerView.OnScrollListener() {
        private val TAG = "RecyclerActivity"

        //1 上滑 2 下滑
        private var slideStatus = 0

        private var firstVisible = 0
        private var lastVisible = 0

        private val max = 10

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

            val layoutManager = recyclerView.layoutManager
            if (layoutManager is LinearLayoutManager) {
                val firstVisible = layoutManager.findFirstVisibleItemPosition()
                val lastVisible = layoutManager.findLastVisibleItemPosition()
                Log.d(
                    TAG,
                    "onScrollStateChanged() called with:, firstVisible = $firstVisible lastVisible=$lastVisible childCount=${layoutManager.childCount} newState=$newState"
                )
                var start = 0
                var end = 0
                when (slideStatus) {
                    1 -> {
                        end = firstVisible
                        start = Math.max(0, end - max)

                    }

                    2 -> {
                        start = lastVisible
                        end = Math.min(layoutManager.itemCount, start + max)

                    }

                }

//                if (refresh && newState == SCROLL_STATE_IDLE && start < end) {
//                    Log.d(
//                        TAG,
//                        "onScrollStateChanged() called with:, start = $start end=$end ${layoutManager.itemCount}"
//                    )
//                    adapter?.setData(list)
//                    adapter?.notifyDataSetChanged()
//                    adapter?.notifyItemRangeChanged(start, end - start)
//                    refresh = false
//                }
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            slideStatus = if (dy > 0) {
                //上滑
                2

            } else {
                //下滑
                1
            }
        }

    }
}