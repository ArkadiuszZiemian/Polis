package com.avangarde.polis.ui.fragment.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.avangarde.polis.databinding.FragmentRegistrationBinding
import com.avangarde.polis.ui.viewmodel.RegistrationViewModel

class RegistrationFragment : Fragment() {
    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private lateinit var regBtn: Button
    private val registrationViewModel by viewModels<RegistrationViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        var emailCondition = false
        var passCondition = false
        regBtn = binding.registerBtn
        binding.emailEt.doOnTextChanged {
            text, _, _, _ ->
            val emailTI = binding.emailContainer
            emailCondition = registrationViewModel.validateEmail(text, emailTI)
            regBtn.isEnabled = emailCondition && passCondition
        }

        binding.passEt.doOnTextChanged {
            text, _, _, _ ->
            val passTI = binding.passContainer
            passCondition = registrationViewModel.validatePassword(text, passTI)
            regBtn.isEnabled = emailCondition && passCondition
        }
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
