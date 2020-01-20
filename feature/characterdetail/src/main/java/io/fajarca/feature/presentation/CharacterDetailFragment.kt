package io.fajarca.feature.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import io.fajarca.characterdetail.R
import io.fajarca.characterdetail.databinding.FragmentCharacterDetailBinding
import io.fajarca.core.MarvelApp
import io.fajarca.core.network.HttpResult
import io.fajarca.core.network.HttpResult.*
import io.fajarca.core.vo.Result
import io.fajarca.feature.di.DaggerCharacterDetailFeatureComponent
import io.fajarca.feature.domain.CharacterDetail
import io.fajarca.presentation.BaseFragment

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>() {


    override fun getLayoutResourceId() = R.layout.fragment_character_detail
    override fun getViewModelClass() = CharacterDetailViewModel::class.java

    override fun initDaggerComponent() {
        DaggerCharacterDetailFeatureComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = arguments?.getString("characterId")?.toInt() ?: 0
        vm.getCharacterDetail(characterId)

        vm.characterDetail.observe(viewLifecycleOwner, Observer { subscribeCharacters(it) })

    }

    private fun subscribeCharacters(it: Result<CharacterDetail>) {
        when(it) {
            is Result.Loading-> {
                showLoading(true)
                binding.uiStateView.showLoading()
            }
            is Result.Success -> {
                showLoading(false)
                refreshData(it.data)
                binding.uiStateView.dismiss()
            }
            is Result.Error -> {
                showLoading(false)
                when(it.cause) {
                    NO_CONNECTION -> binding.uiStateView.showNoInternetConnection()
                    else -> binding.uiStateView.showError("Unknown error")
                }
            }
        }
    }


    private fun refreshData(character : CharacterDetail) {
        binding.character = character
    }

    private fun showLoading(isLoading :Boolean) {
        binding.isLoading = isLoading
    }

}
