package com.picpay.desafio.android.Domain.model.usecase.preferences

import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.repository.preferences.PreferencesHelperRepository

class SetUsersListUseCase(
    private val repository: PreferencesHelperRepository
) {
    fun setUsersListUseCase(users: List<UserDomain>) {
        repository.setUsersListShowed(users)
    }
}