package com.yasin.widget

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.yasin.view.recyclerview.DividerItemDecoration
import com.yasin.widget.recycler.RecyclerActivity

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val featureRv:RecyclerView=findViewById(R.id.rv)


//        1. 线性布局
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        featureRv.setLayoutManager(linearLayoutManager)
        val dividerItemDecoration: ItemDecoration = DividerItemDecoration.Builder(this)
            .orientation(LinearLayoutManager.VERTICAL)
            .build()
        featureRv.addItemDecoration(dividerItemDecoration)

        val adapter=FeatureAdapter(this)
        adapter.setData(createData())

        adapter.setOnItemClickListener { holder, position ->
            when(position){
                0->{
                    val intent=Intent(this,
                        RecyclerActivity::class.java);
                    startActivity(intent)
                }


            }
        }

        featureRv.adapter=adapter

    }


    private fun createData():MutableList<String>{
        val list= mutableListOf<String>()
        list.add("RecyclerView")

        return list
    }



}