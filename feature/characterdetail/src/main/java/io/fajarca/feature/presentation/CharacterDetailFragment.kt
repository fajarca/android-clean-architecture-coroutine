package io.fajarca.feature.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import io.fajarca.characterdetail.R
import io.fajarca.characterdetail.databinding.FragmentCharacterDetailBinding
import io.fajarca.core.MarvelApp
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
            }
            is Result.Success -> {
                showLoading(false)
                refreshData(it.data)
            }
            is Result.Error -> {
                showLoading(false)
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