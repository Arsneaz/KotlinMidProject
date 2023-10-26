package com.example.kotlinmidproject.ui.home

// The F1 Api will only load 1 image per time, doesn't good for recyclerView
// I can on the response API (non 404 http code) but the data seems misisng
// Idk, maybe I can ask Pak Abdu Later
// Using the cat image seems a good option

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlinmidproject.api.RetrofitInstance
import com.example.kotlinmidproject.api.SportsApi
import com.example.kotlinmidproject.databinding.FragmentHomeBinding
import com.example.kotlinmidproject.model.PersonJson
import com.example.kotlinmidproject.util.Constant
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.security.auth.callback.Callback

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var ApiClient: SportsApi

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        ApiClient = retrofit.create(SportsApi::class.java)

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)

        retriveData()

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun retriveData() {
        val call = ApiClient.getData()
    }

}