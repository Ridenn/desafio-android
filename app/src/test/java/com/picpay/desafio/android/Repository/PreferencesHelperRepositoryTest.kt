package com.picpay.desafio.android.Repository

import com.picpay.desafio.android.Data.local.preferences.PreferencesHelper
import com.picpay.desafio.android.Data.local.preferences.repository.preferences.PreferencesHelperRepositoryImpl
import com.picpay.desafio.android.Data.utils.DataFactory
import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.repository.preferences.PreferencesHelperRepository
import com.picpay.desafio.android.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(JUnit4::class)
class PreferencesHelperRepositoryTest {

    private val helper = mock<PreferencesHelper>()

    private lateinit var repository: PreferencesHelperRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        repository = PreferencesHelperRepositoryImpl(helper)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should return users list`() =
        mainCoroutineRule.runBlockingTest {

            val mockedResponse = listOf(
                UserDomain(
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomInteger(),
                    DataFactory.randomString()
                )
            )

            whenever(helper.getUsersListShowed()).thenReturn(mockedResponse)

            val response = repository.getUsersListShowed()

            Assert.assertEquals(mockedResponse, response)

        }

    @ExperimentalCoroutinesApi
    @Test
    fun `should verify set users list is called`() =
        mainCoroutineRule.runBlockingTest {
            val mockedList = listOf(
                UserDomain(
                    DataFactory.randomString(),
                    DataFactory.randomString(),
                    DataFactory.randomInteger(),
                    DataFactory.randomString()
                )
            )
            repository.setUsersListShowed(mockedList)
            verify(helper, times(1)).setUsersListShowed(mockedList)
        }
}