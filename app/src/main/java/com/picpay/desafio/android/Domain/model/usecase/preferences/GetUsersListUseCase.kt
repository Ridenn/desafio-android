package com.picpay.desafio.android.Domain.model.usecase.preferences

import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.repository.preferences.PreferencesHelperRepository

class GetUsersListUseCase(
    private val repository: PreferencesHelperRepository
) {
    fun getUsersListUseCase(): List<UserDomain>? = repository.getUsersListShowed()
}