package com.picpay.desafio.android.Data.local.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.Domain.model.UserDomain
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type

class PreferencesHelperImpl(context: Context, moshiParam: Moshi) : PreferencesHelper {

    companion object {
        private const val dataStoreName = BuildConfig.APPLICATION_ID

        private val PREF_USERS_LIST = stringPreferencesKey("PREF_USERS_LIST")
    }

    private val Context.dataStore by preferencesDataStore(
        name = dataStoreName,
        produceMigrations = { context ->
            listOf(SharedPreferencesMigration(context, dataStoreName))
        }
    )

    private val dataStore: DataStore<Preferences> = context.dataStore

    private val moshi = moshiParam

    private val userType: Type =
        Types.newParameterizedType(List::class.java, UserDomain::class.java)

    private val userAdapter: JsonAdapter<List<UserDomain>> =
        moshi.adapter(userType)

    override fun setUsersListShowed(users: List<UserDomain>) {
        usersList = userAdapter.toJson(users)
    }

    override fun getUsersListShowed(): List<UserDomain>? {
        usersList?.let { list ->
            return userAdapter.fromJson(list)?.toList()
        } ?: run {
            return null
        }
    }

    override fun clear() {
        usersList = null
    }

    private var usersList by PreferencesDataStore(
        dataStore = dataStore,
        key = PREF_USERS_LIST,
        defaultValue = null,
        context
    )
}