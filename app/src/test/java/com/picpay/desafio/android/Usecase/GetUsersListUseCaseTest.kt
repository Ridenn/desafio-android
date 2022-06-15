package com.picpay.desafio.android.Usecase

import com.picpay.desafio.android.Data.utils.DataFactory
import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.model.usecase.preferences.GetUsersListUseCase
import com.picpay.desafio.android.Domain.repository.preferences.PreferencesHelperRepository
import com.picpay.desafio.android.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class GetUsersListUseCaseTest {
    private lateinit var useCase: GetUsersListUseCase

    @Mock
    lateinit var repository: PreferencesHelperRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupDispatcher() {
        MockitoAnnotations.openMocks(this)
        useCase = GetUsersListUseCase(repository)
    }

    @Test
    fun `should get user list then returns data`() =
        mainCoroutineRule.runBlockingTest {
            val mockedResponse = listOf(
                UserDomain(
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomInteger(),
                    DataFactory.randomString()
                )
            )

            whenever(repository.getUsersListShowed()).thenReturn(mockedResponse)

            val response = useCase.getUsersListUseCase()

            Assert.assertEquals(mockedResponse, response)
        }




}