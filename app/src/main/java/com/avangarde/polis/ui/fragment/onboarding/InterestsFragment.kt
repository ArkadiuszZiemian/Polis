package com.avangarde.polis.ui.fragment.onboarding

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avangarde.polis.R
import com.avangarde.polis.databinding.FragmentInterestsBinding
import com.avangarde.polis.ui.viewmodel.InterestsViewModel

class InterestsFragment : Fragment() {
    private var _binding: FragmentInterestsBinding? = null
    private val binding get() = _binding!!
    private val interestsViewModel by viewModels <InterestsViewModel> ()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        handleBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInterestsBinding.inflate(inflater, container, false)
        val btn = binding.confBtn
        btn.setOnClickListener {
            findNavController().navigate(
                R.id.action_interestsFragment_to_homeFragment
            )
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleBackPress() {
        val backPressCallback = object : OnBackPressedCallback(
            true
        ) {
            override fun handleOnBackPressed() {
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            backPressCallback
        )
    }
}
