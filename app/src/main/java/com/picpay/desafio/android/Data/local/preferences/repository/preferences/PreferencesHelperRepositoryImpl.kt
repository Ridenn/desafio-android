package com.picpay.desafio.android.Data.local.preferences.repository.preferences

import com.picpay.desafio.android.Data.local.preferences.PreferencesHelper
import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.repository.preferences.PreferencesHelperRepository

class PreferencesHelperRepositoryImpl(
    private val preferencesHelper: PreferencesHelper
) : PreferencesHelperRepository {

    override fun setUsersListShowed(users: List<UserDomain>) {
        preferencesHelper.setUsersListShowed(users)
    }

    override fun getUsersListShowed(): List<UserDomain>? {
        return preferencesHelper.getUsersListShowed()
    }
}