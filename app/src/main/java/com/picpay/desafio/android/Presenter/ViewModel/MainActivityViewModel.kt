package com.picpay.desafio.android.Presenter.ViewModel

import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.model.usecase.preferences.GetUsersListUseCase
import com.picpay.desafio.android.Domain.model.usecase.preferences.SetUsersListUseCase

class MainActivityViewModel(
    private val getUsersListUseCase: GetUsersListUseCase,
    private val setUsersListUseCase: SetUsersListUseCase
) : ViewModel() {
    fun setUsersList(users: List<UserDomain>) {
        setUsersListUseCase.setUsersListUseCase(users)
    }

    fun getUsersList() = getUsersListUseCase.getUsersListUseCase()
}