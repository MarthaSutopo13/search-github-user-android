package com.martha.core_base.network

import com.martha.core_base.network.repo.UserRepo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiHelper @Inject
constructor() {
    val userRepo = UserRepo()
}