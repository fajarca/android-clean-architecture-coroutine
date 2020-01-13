package io.fajarca.feature.presentation

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import io.fajarca.characterdetail.R
import io.fajarca.characterdetail.databinding.FragmentCharacterDetailBinding
import io.fajarca.core.MarvelApp
import io.fajarca.core.common.Result
import io.fajarca.feature.data.CharacterDetailDto
import io.fajarca.feature.di.DaggerCharacterDetailFeatureComponent
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

        vm.getCharacterDetail(1009262)

        vm.characters.observe(viewLifecycleOwner, Observer { subscribeCharacters(it) })

    }

    private fun subscribeCharacters(it: Result<CharacterDetailDto>) {
        when(it) {
            is Result.Loading-> {

            }
            is Result.Success -> {

            }
            is Result.Error -> {

            }
        }
    }



}
