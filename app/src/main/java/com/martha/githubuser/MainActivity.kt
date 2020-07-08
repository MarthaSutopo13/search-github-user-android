package com.martha.githubuser

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.error.ANError
import com.martha.core_base.base.BaseActivity
import com.martha.core_base.listener.OnLoadMoreListener
import com.martha.core_base.model.UserResponse
import com.martha.core_base.network.ResponseOkHttp
import com.martha.githubuser.adapter.UserAdapter
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Response

class MainActivity : BaseActivity() {

    private val TAG = "Search User"
    private lateinit var userAdapter : UserAdapter
    val layoutManager = LinearLayoutManager(this)
    lateinit var onLoadMoreListener: OnLoadMoreListener
    val users: MutableList<UserResponse.User> = mutableListOf()
    var perPage = 10
    var search = ""

    override fun setLayout(): Int {
       return R.layout.activity_main
    }

    override fun onInitializedView(savedInstanceState: Bundle?) {
        initView()
        initAction()
    }

    fun initView() {
        rv_user.layoutManager = layoutManager
        userAdapter = UserAdapter(this)
        rv_user.adapter = userAdapter
    }

    fun initAction() {
        et_search.setOnEditorActionListener(object: TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    search = et_search.text.toString()
                    getListUser(et_search.text.toString())
                    return true
                }

                return false
            }
        })

        sr_user.setOnRefreshListener {
            sr_user.isRefreshing = false
        }

        onLoadMoreListener = object : OnLoadMoreListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                onLoadMoreListener.counter = 5
                perPage += 5
                getMoretListUser(perPage)
                var currentSize = userAdapter.itemCount
                view?.post { userAdapter.notifyItemRangeInserted(currentSize, userAdapter.itemCount - 1) }
            }
        }

        rv_user.addOnScrollListener(onLoadMoreListener)
    }

    fun getListUser(name: String) {
        apiHelper.userRepo.getListUser(name, perPage).getAsOkHttpResponseAndObject(
            UserResponse::class.java,
        object : ResponseOkHttp<UserResponse>(200){

            override fun onSuccess(response: Response, model: UserResponse) {
                if (model.items!! == null) {
                    userAdapter.clearUser()
                }

                model.items?.let {
                    sr_user.visibility = View.VISIBLE
                    ll_desc.visibility = View.GONE
                    userAdapter.setUser(it)
                }
            }

            override fun onUnauthorized() {
                Log.e(TAG, "authorization")
            }

            override fun onFailed(response: Response, model: UserResponse) {
                Log.e(TAG, response.message())
            }

            override fun onHasError(error: ANError) {
                if (error.errorCode == 403) {
                    sr_user.visibility = View.GONE
                    ll_desc.visibility = View.VISIBLE
                }
                Log.e(TAG, error.errorDetail)
            }
        })
    }

    fun getMoretListUser(page: Int) {
        apiHelper.userRepo.getListUser(search, page).getAsOkHttpResponseAndObject(
            UserResponse::class.java,
        object : ResponseOkHttp<UserResponse>(200){

            override fun onSuccess(response: Response, model: UserResponse) {
                if(model.items.orEmpty().isNotEmpty())  {
                    for (i in model.items!!) {
                        users.add(i)
                    }
                    sr_user.visibility = View.VISIBLE
                    ll_desc.visibility = View.GONE
                    userAdapter.setUser(users)
                }
            }

            override fun onUnauthorized() {
                Log.e(TAG, "authorization")
            }

            override fun onFailed(response: Response, model: UserResponse) {
                Log.e(TAG, response.message())
            }

            override fun onHasError(error: ANError) {
                if (error.errorCode == 403) {
                    sr_user.visibility = View.GONE
                    ll_desc.visibility = View.VISIBLE
                }
                Log.e(TAG, error.errorDetail)
            }
        })
    }
}