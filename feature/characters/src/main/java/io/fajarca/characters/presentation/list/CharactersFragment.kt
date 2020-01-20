package io.fajarca.characters.presentation.list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.core.MarvelApp
import io.fajarca.characters.R
import io.fajarca.characters.databinding.FragmentHomeBinding
import io.fajarca.characters.di.DaggerFeatureComponent
import io.fajarca.characters.domain.MarvelCharacter
import io.fajarca.presentation.BaseFragment
import io.fajarca.presentation.extension.navigateTo

class CharactersFragment : BaseFragment<FragmentHomeBinding, CharactersViewModel>(),
    CharactersRecyclerAdapter.CharacterClickListener {

    private lateinit var adapter : CharactersRecyclerAdapter

    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = CharactersViewModel::class.java

    override fun initDaggerComponent() {
        DaggerFeatureComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        vm.getAllCharacters()

        vm.characters.observe(viewLifecycleOwner, Observer { subscribeCharacters(it) })

    }

    private fun subscribeCharacters(it: CharactersViewModel.CharacterState<List<MarvelCharacter>>) {
        when(it) {
            is CharactersViewModel.CharacterState.Loading -> {
                showLoading(true)
                binding.uiStateView.showLoading()
            }
            is CharactersViewModel.CharacterState.Empty -> {
                showLoading(false)
                binding.uiStateView.showEmptyData()
            }
            is CharactersViewModel.CharacterState.Success -> {
                showLoading(false)
                binding.uiStateView.dismiss()
                refreshData(it.data)
            }
        }
    }


    private fun initRecyclerView() {
        adapter = CharactersRecyclerAdapter(this)
        val layoutManager = GridLayoutManager(activity,2 )
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun refreshData(characters: List<MarvelCharacter>) {
        adapter.submitList(characters)
    }


    override fun onCharacterPressed(character: MarvelCharacter) {
        navigateTo("app://characterdetail/${character.id}")
    }

    private fun showLoading(isLoading :Boolean) {
        binding.isLoading = isLoading
    }

}
