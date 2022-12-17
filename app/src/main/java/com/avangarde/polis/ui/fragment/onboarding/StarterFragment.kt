package com.avangarde.polis.ui.fragment.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.avangarde.polis.App
import com.avangarde.polis.R
import com.avangarde.polis.configuration.BaseIdp
import com.avangarde.polis.configuration.Idp
import com.avangarde.polis.configuration.IdpWithPkce
import com.avangarde.polis.databinding.FragmentStarterBinding
import com.avangarde.polis.di.component.DaggerScreenComponent
import com.avangarde.polis.helper.ViewModelFactory
import com.avangarde.polis.ui.viewmodel.StarterViewModel
import com.squareup.moshi.Moshi
import javax.inject.Inject
import kotlinx.coroutines.launch

class StarterFragment : Fragment() {
    @Inject
    lateinit var jsonConverter: Moshi
    private val authLauncher = registerAuthLauncher()
    private val appComponent by lazy { (activity?.application as App).getAppComponent() }
    private val authDataStoreComponent by lazy { (activity?.application as App).getAuthDataStoreComponent() }
    private val screenComponent by lazy {
        DaggerScreenComponent.factory().create(appComponent, authDataStoreComponent)
    }
    private val starterViewModel by viewModels<StarterViewModel> {
        ViewModelFactory {
            screenComponent.getStarterViewModel().create(appComponent)
        }
    }
    private var _binding: FragmentStarterBinding? = null
    private val binding get() = _binding!!
    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
        handleBackPress()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val email = starterViewModel.getLoginEmail()
        if (email != null)
            findNavController().navigate(
                StarterFragmentDirections.actionStarterFragmentToHomeFragment(email)
            )
        _binding = FragmentStarterBinding.inflate(inflater, container, false)
        binding.twitterBtn.setOnClickListener {
            idpLoginButtonListener(getIdpConfig(R.raw.twitter_idp, IdpWithPkce::class.java))
        }
        binding.facebookBtn.setOnClickListener {
            idpLoginButtonListener(getIdpConfig(R.raw.facebook_idp, BaseIdp::class.java))
        }
        binding.googleBtn.setOnClickListener {
            idpLoginButtonListener(getIdpConfig(R.raw.google_idp, IdpWithPkce::class.java))
        }
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_starterFragment_to_loginFragment)
        }
        binding.registerBtn.setOnClickListener {
            findNavController().navigate(R.id.action_starterFragment_to_registrationFragment)
        }
        lifecycleScope.launch {
            starterViewModel.isLoggedIn.collect {
                if (it)
                    findNavController().navigate(R.id.action_starterFragment_to_interestsFragment)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getIdpConfig(jsonResource: Int, idp: Class<out Idp>): Idp? {
        val jsonStr = resources.openRawResource(jsonResource).bufferedReader().use { it.readText() }
        val jsonAdapter = jsonConverter.adapter(idp)
        return jsonAdapter.fromJson(jsonStr)
    }

    private fun idpLoginButtonListener(idpConfig: Idp?) {
        idpConfig?.let {
            starterViewModel.executeAuthLauncher(
                authLauncher, requireContext().packageManager,
                it
            )
            starterViewModel.secret = it.clientSecret
        } ?: throw IllegalStateException("Idp properties not loaded from JSON file")
    }

    private fun registerAuthLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            starterViewModel.launchAuthRequest(it)
        }
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
