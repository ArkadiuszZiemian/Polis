package com.avangarde.polis.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.avangarde.polis.databinding.FragmentCityInfoBinding
import com.avangarde.polis.ui.viewmodel.CityInfoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CityInfoFragment : Fragment() {

    private val viewModel: CityInfoViewModel by activityViewModels()
    private var _binding: FragmentCityInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityInfoBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}