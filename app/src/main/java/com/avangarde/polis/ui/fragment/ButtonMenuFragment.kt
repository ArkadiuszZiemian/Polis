package com.avangarde.polis.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.avangarde.polis.R
import com.avangarde.polis.databinding.FragmentButtonMenuBinding

class ButtonMenuFragment : Fragment() {
    private var _binding: FragmentButtonMenuBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentButtonMenuBinding.inflate(inflater, container, false)
        binding.interestsPanelBtn.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_interestsFragment)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
