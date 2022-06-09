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
import com.picpay.desafio.android.Data.model.User
import com.picpay.desafio.android.Data.model.toDomain
import com.picpay.desafio.android.Domain.model.UserDomain
import com.picpay.desafio.android.Domain.model.toRemote
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ActivityMainBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    var usersArray: ArrayList<UserDomain> = ArrayList()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter

    private val url = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    private val gson: Gson by lazy { GsonBuilder().create() }

    private val okHttp: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(url)
            .client(okHttp)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val service: PicPayService by lazy {
        retrofit.create(PicPayService::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fetchData(savedInstanceState)
    }

    private fun fetchData(savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        progressBar = binding.userListProgressBar

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE

        if (savedInstanceState == null) {
            service.getUsers()
                .enqueue(object : Callback<List<User>> {
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        val message = getString(R.string.error)

                        progressBar.visibility = View.GONE
                        recyclerView.visibility = View.GONE

                        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        progressBar.visibility = View.GONE

                        adapter.users = response.body()!!

                        usersArray = ArrayList(
                            response.body()!!.map {
                                it.toDomain()
                            })
                    }
                })
        } else {
            progressBar.visibility = View.GONE
            usersArray = savedInstanceState.getParcelableArrayList("users")!!
            adapter.users = usersArray.map {
                it.toRemote()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("users", usersArray)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

    }
}