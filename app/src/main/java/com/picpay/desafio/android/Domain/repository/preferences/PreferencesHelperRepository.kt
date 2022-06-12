package com.picpay.desafio.android.Domain.repository.preferences

import com.picpay.desafio.android.Domain.model.UserDomain

interface PreferencesHelperRepository {
    fun setUsersListShowed(users: List<UserDomain>)
    fun getUsersListShowed(): List<UserDomain>?
}