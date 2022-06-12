package com.picpay.desafio.android.Data.local.preferences

import com.picpay.desafio.android.Domain.model.UserDomain

interface PreferencesHelper {
    fun clear()
    fun setUsersListShowed(users: List<UserDomain>)
    fun getUsersListShowed(): List<UserDomain>?
}