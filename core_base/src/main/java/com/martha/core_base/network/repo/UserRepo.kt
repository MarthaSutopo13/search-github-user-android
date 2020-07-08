package com.martha.core_base.network.repo

import com.martha.core_base.network.ApiConstant
import com.martha.core_base.network.ApiEndpoint
import com.martha.core_base.network.RestApi
import com.rx2androidnetworking.Rx2ANRequest

class UserRepo {
    fun getListUser(name: String, perPage: Int) : Rx2ANRequest {
        val params = HashMap<String, String>()
        params[ApiConstant.Q] = name
        params[ApiConstant.PER_PAGE] = perPage.toString()
        return RestApi.get(ApiEndpoint.SEARCH_USER, params, null, null)
    }
}