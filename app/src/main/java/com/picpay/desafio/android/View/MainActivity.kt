package com.picpay.desafio.android.View

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.picpay.desafio.android.Adapter.UserListAdapter
import com.picpay.desafio.android.Data.PicPayService
import com.picpay.desafio.android.Data.local.preferences.PreferencesHelperImpl
import com.picpay.desafio.android.Data.model.User
import com.picpay.desafio.android.Data.model.toDomain
import com.picpay.desafio.android.Data.modules.ApiModule
import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.model.toRemote
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private val customSharedPreferences: PreferencesHelperImpl
        get() = PreferencesHelperImpl(this, Moshi.Builder().build())
//    private val mainViewModel: MainActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding

    var usersArray: ArrayList<UserDomain> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val service = ApiModule().providesRetrofit()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configPullToRefresh(savedInstanceState)
        fetchData(savedInstanceState)
    }

    private fun configPullToRefresh(savedInstanceState: Bundle?) {
        binding.contactsRefreshLayout.setOnRefreshListener {
            customSharedPreferences.clear()
            fetchData(savedInstanceState)
        }
    }

    private fun fetchData(savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        showProgressBar(View.VISIBLE)

        if (savedInstanceState == null) {

            val cachedUsersList = getCachedUsersList()

            if (!cachedUsersList.isNullOrEmpty()) {
                adapter.users = cachedUsersList.map {
                    it.toRemote()
                }
                showProgressBar(View.GONE)
            } else {
                service.getUsers()
                    .enqueue(object : Callback<List<User>> {

                        override fun onFailure(call: Call<List<User>>, t: Throwable) {
                            val message = getString(R.string.error)

                            showProgressBar(View.GONE)
                            recyclerView.visibility = View.GONE

                            Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        override fun onResponse(
                            call: Call<List<User>>,
                            response: Response<List<User>>
                        ) {
                            showProgressBar(View.GONE)

                            adapter.users = response.body()!!

                            usersArray = ArrayList(
                                response.body()!!.map {
                                    it.toDomain()
                                })

                            cacheUsersList(usersArray)
                        }
                    })
            }
        } else {
            showProgressBar(View.GONE)
            usersArray = savedInstanceState.getParcelableArrayList("users")!!
            adapter.users = usersArray.map {
                it.toRemote()
            }
        }
        binding.contactsRefreshLayout.isRefreshing = false
    }

    private fun showProgressBar(value: Int) {
        progressBar.visibility = value
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("users", usersArray)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }

    private fun cacheUsersList(users: ArrayList<UserDomain>) {
        customSharedPreferences.setUsersListShowed(users)
//        mainViewModel.setUsersList(users)
    }

    private fun getCachedUsersList() =
        customSharedPreferences.getUsersListShowed()
//        mainViewModel.getUsersList()
}