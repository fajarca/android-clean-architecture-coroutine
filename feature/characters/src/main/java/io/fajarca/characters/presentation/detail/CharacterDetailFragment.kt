package io.fajarca.characters.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import io.fajarca.characters.R
import io.fajarca.characters.databinding.FragmentCharacterDetailBinding
import io.fajarca.characters.di.DaggerCharacterDetailFeatureComponent
import io.fajarca.characters.domain.CharacterDetail
import io.fajarca.core.MarvelApp
import io.fajarca.core.network.HttpResult.NO_CONNECTION
import io.fajarca.core.vo.Result
import io.fajarca.presentation.BaseFragment
import javax.inject.Inject

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>() {

    @Inject
    lateinit var factory: CharacterDetailViewModel.Factory

    override val vm: CharacterDetailViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_character_detail

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
