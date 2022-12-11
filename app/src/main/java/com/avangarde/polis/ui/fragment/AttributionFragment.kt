package com.avangarde.polis.ui.fragment

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.avangarde.polis.databinding.FragmentAttributionBinding

class AttributionFragment : Fragment() {
    private var _binding: FragmentAttributionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAttributionBinding.inflate(inflater, container, false)
        binding.attrib1.movementMethod = LinkMovementMethod.getInstance()
        binding.attrib2.movementMethod = LinkMovementMethod.getInstance()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
