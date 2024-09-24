package com.example.testapp.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.FragmentAuthBinding
import com.google.android.material.tabs.TabLayout

class AuthFragment : Fragment() {

    private lateinit var binding: FragmentAuthBinding
    private lateinit var loginLayout: View
    private lateinit var registerLayout: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_auth, container, false)

        val tabLayout: TabLayout = binding.tabLayout
        loginLayout = binding.loginLayout
        registerLayout = binding.registerLayout
        val buttonLogin: Button = binding.buttonLogin
        val buttonRegister: Button = binding.buttonRegister

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

        buttonLogin.setOnClickListener {
            findNavController().navigate(R.id.action_global_galleryFragment2)
        }

        buttonRegister.setOnClickListener {

        }

        return binding.root
    }

}