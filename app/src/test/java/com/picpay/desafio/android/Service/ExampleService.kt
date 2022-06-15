package com.picpay.desafio.android.Service

import com.picpay.desafio.android.Data.PicPayService
import com.picpay.desafio.android.Data.model.User

class ExampleService(
    private val service: PicPayService
) {

    fun example(): List<User> {
        val users = service.getUsers().execute()

        return users.body() ?: emptyList()
    }
}