package com.avangarde.polis.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

class HomeFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
        handleBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.avangarde.polis.R.layout.fragment_home, container, false)
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
