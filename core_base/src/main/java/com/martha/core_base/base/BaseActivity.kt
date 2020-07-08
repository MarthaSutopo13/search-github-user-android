package com.martha.core_base.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.martha.core_base.network.ApiHelper

abstract class BaseActivity: AppCompatActivity(), BaseActivityView {
    lateinit var mContext: Context
    lateinit var apiHelper: ApiHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(setLayout())
        initActivity()
        onInitializedView(savedInstanceState)
    }

    fun initActivity() {
        mContext = this
        apiHelper = ApiHelper()
    }

    abstract fun setLayout() : Int
    protected abstract fun onInitializedView(savedInstanceState: Bundle?)

    override fun getContext(): Context? {
        return mContext
    }
}