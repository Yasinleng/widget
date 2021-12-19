package com.yasin.widget.utils

import android.os.Handler
import android.os.HandlerThread

/**
 * 邮箱：lengyacheng@163.com
 * Created by yasin on 2021/12/18.
 */
class PollThread(threadName:String="PollThread") {

    private var mHandler: Handler? = null

    private val minute = 60 * 1000L

    private val interval = 1

    @Volatile
    private var start = false


    private var task:Runnable?=null

    init {
//        mHandler = Handler(Looper.getMainLooper()) // 全局变量

        val thread = HandlerThread(threadName)
        thread.start()
        mHandler = Handler(thread.looper)
    }


    fun start(action: Action) {
        if (task == null) {
            task = object : Runnable {
                override fun run() {
                    action.start()
                    if (start)
                        mHandler?.postDelayed(this, 1000)
                }
            }
        }
        if (!start) {
            mHandler?.post(task!!)
            start = true
        }
    }

    fun stop() {
        start = false
    }

    fun release(){
        stop()
        task?.let { mHandler?.removeCallbacks(it) }
    }

    interface Action {
        fun start()
    }
}