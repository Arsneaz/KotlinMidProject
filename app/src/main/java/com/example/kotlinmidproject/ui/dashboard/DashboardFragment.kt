package com.example.kotlinmidproject.ui.dashboard

import UserPreferencesManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.kotlinmidproject.LoginActivity
import com.example.kotlinmidproject.databinding.FragmentDashboardBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

            lifecycleScope.launch{
                UserPreferencesManager.initialize(requireContext())
                val userPreferences = UserPreferencesManager.getUserPreferences()

                val username = userPreferences.getUsername().first()
                Log.d(username, "Username DataStore")

                val userData = userPreferences.getUserData(username = username)

                Log.d(userData.username, "username")
                binding.image.text = userData.username?.firstOrNull()?.toString() ?: ""
                Log.d(userData.nik, "nik")
                binding.username.setText(userData.username)
                binding.NIK.setText(userData.nik)
            }


        binding.btnLogout.setOnClickListener{
            lifecycleScope.launch {
                UserPreferencesManager.initialize(requireContext())
                val userPreferences = UserPreferencesManager.getUserPreferences()
                userPreferences.setLoggedIn(false)

                val intent = Intent(requireActivity(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}