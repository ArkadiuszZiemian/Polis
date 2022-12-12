package com.avangarde.polis.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.avangarde.polis.App
import com.avangarde.polis.R
import com.avangarde.polis.databinding.FragmentCityInfoBinding
import com.avangarde.polis.di.component.DaggerScreenComponent
import com.avangarde.polis.helper.ViewModelFactory
import com.avangarde.polis.ui.viewmodel.CityInfoViewModel

class CityInfoFragment : Fragment() {
    private val appComponent by lazy { (activity?.application as App).getAppComponent() }
    private val authDataStoreComponent by lazy { (activity?.application as App).getAuthDataStoreComponent() }
    private val screenComponent by lazy {
        DaggerScreenComponent.factory().create(appComponent, authDataStoreComponent)
    }
    private val cityInfoViewModel by viewModels<CityInfoViewModel> {
        ViewModelFactory {
            screenComponent.getCityInfoViewModel()
        }
    }
    private var _binding: FragmentCityInfoBinding? = null
    private val binding get() = _binding!!
    private var menuInflater: MenuInflater? = null
    private val popup by lazy { PopupMenu(requireActivity(), binding.popupBtn) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCityInfoBinding.inflate(inflater, container, false)
        binding.popupBtn.setOnClickListener {
            showPopup()
        }
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.logout_mi -> {
                    findNavController().navigate(R.id.action_homeFragment_to_starterFragment)
                    cityInfoViewModel.signOut()
                    true
                }
                R.id.attribution_mi -> {
                    findNavController().navigate(R.id.action_homeFragment_to_attributionFragment)
                    true
                }
                else -> false
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showPopup() {
        if (menuInflater == null) {
            menuInflater = popup.menuInflater
            (menuInflater as MenuInflater).inflate(R.menu.settings_menu, popup.menu)
        }
        popup.show()
    }
}
