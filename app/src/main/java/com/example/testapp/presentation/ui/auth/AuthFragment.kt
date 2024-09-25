package com.example.testapp.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentAuthBinding
import com.example.testapp.presentation.ui.util.ScreenUiState
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var loginLayout: View
    private lateinit var registerLayout: View
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)

        initViews()
        setupTabLayout()
        setupButtonListeners()
        observeSignUp()
        observeLogIn()

        return binding.root
    }

    private fun initViews() {
        loginLayout = binding.loginLayout
        registerLayout = binding.registerLayout
    }

    private fun setupTabLayout() {
        val tabLayout: TabLayout = binding.tabLayout
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        loginLayout.visibility = View.VISIBLE
                        registerLayout.visibility = View.GONE
                    }

                    1 -> {
                        loginLayout.visibility = View.GONE
                        registerLayout.visibility = View.VISIBLE
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun setupButtonListeners() {
        val buttonLogin: Button = binding.buttonLogin
        val buttonRegister: Button = binding.buttonRegister

        buttonLogin.setOnClickListener {
            val loginLogin = binding.editTextLoginL.text.toString()
            val passwordLogin = binding.editTextPasswordLogin.text.toString()
            authViewModel.signIn(loginLogin, passwordLogin)
        }

        buttonRegister.setOnClickListener {
            val loginRegister = binding.editTextLoginRegister.text.toString()
            val passwordRegister = binding.editTextPasswordRegister.text.toString()
            authViewModel.signUp(loginRegister, passwordRegister)
        }
    }

    private fun observeSignUp() {
        authViewModel.signUp.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ScreenUiState.Success -> {
                    findNavController().navigate(R.id.action_global_galleryFragment2)
                }

                is ScreenUiState.Empty -> {
                }

                is ScreenUiState.Error -> {
                }

                is ScreenUiState.Loading -> {
                }
            }
        }
    }

    private fun observeLogIn() {
        authViewModel.logIn.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ScreenUiState.Success -> {
                    findNavController().navigate(R.id.action_global_galleryFragment2)
                }

                is ScreenUiState.Empty -> {
                }

                is ScreenUiState.Error -> {
                }

                is ScreenUiState.Loading -> {
                }
            }
        }
    }
}
