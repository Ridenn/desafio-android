package com.picpay.desafio.android.Usecase

import com.picpay.desafio.android.Data.utils.DataFactory
import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.model.usecase.preferences.SetUsersListUseCase
import com.picpay.desafio.android.Domain.repository.preferences.PreferencesHelperRepository
import com.picpay.desafio.android.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
class SetUsersListUseCaseTest {
    private lateinit var useCase: SetUsersListUseCase

    @Mock
    lateinit var repository: PreferencesHelperRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupDispatcher() {
        MockitoAnnotations.openMocks(this)
        useCase = SetUsersListUseCase(repository)
    }

    @Test
    fun `should set user list then verify repository is called`() =
        mainCoroutineRule.runBlockingTest {
            val mockedList = listOf(
                UserDomain(
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomInteger(),
                    DataFactory.randomString()
                )
            )

            useCase.setUsersListUseCase(mockedList)
            verify(repository, times(1)).setUsersListShowed(mockedList)
        }
}