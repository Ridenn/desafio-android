package com.picpay.desafio.android.Domain.di

import com.picpay.desafio.android.Data.local.preferences.PreferencesHelper
import com.picpay.desafio.android.Data.local.preferences.PreferencesHelperImpl
import com.picpay.desafio.android.Data.local.preferences.repository.preferences.PreferencesHelperRepositoryImpl
import com.picpay.desafio.android.Domain.repository.preferences.PreferencesHelperRepository
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val usersListModule = module {
    factory<PreferencesHelper> { PreferencesHelperImpl(androidContext(), Moshi.Builder().build()) }
    single<PreferencesHelperRepository> { PreferencesHelperRepositoryImpl(get()) }
}