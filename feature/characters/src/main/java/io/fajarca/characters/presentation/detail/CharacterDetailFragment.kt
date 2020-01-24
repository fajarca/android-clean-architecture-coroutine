package io.fajarca.characters.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import io.fajarca.characters.R
import io.fajarca.characters.databinding.FragmentCharacterDetailBinding
import io.fajarca.characters.di.DaggerCharacterDetailFeatureComponent
import io.fajarca.characters.domain.entities.MarvelCharacterDetail
import io.fajarca.characters.domain.entities.MarvelCharacterSeriesUiModel
import io.fajarca.core.MarvelApp
import io.fajarca.core.network.HttpResult.NO_CONNECTION
import io.fajarca.core.vo.Result
import io.fajarca.presentation.BaseFragment
import javax.inject.Inject

class CharacterDetailFragment : BaseFragment<FragmentCharacterDetailBinding, CharacterDetailViewModel>(),
    CharacterSeriesRecyclerAdapter.SeriesClickListener {

    @Inject
    lateinit var factory: CharacterDetailViewModel.Factory

    override val vm: CharacterDetailViewModel by viewModels(factoryProducer = { factory })

    override fun getLayoutResourceId() = R.layout.fragment_character_detail

    private val args  : CharacterDetailFragmentArgs by navArgs()

    private lateinit var adapter: CharacterSeriesRecyclerAdapter

    override fun initDaggerComponent() {
        DaggerCharacterDetailFeatureComponent
            .builder()
            .coreComponent(MarvelApp.coreComponent(requireContext()))
            .build()
            .inject(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characterId = args.characterId

        initRecyclerView()

        vm.getCharacterDetail(characterId)
        vm.getCharacterSeries(characterId)

        vm.characterDetail.observe(viewLifecycleOwner, Observer { subscribeCharacters(it) })
        vm.series.observe(this, Observer { subscribeCharactersSeries(it) })
    }

    private fun subscribeCharacters(it: Result<MarvelCharacterDetail>) {
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

    private fun subscribeCharactersSeries(it: CharacterDetailViewModel.SeriesState<List<MarvelCharacterSeriesUiModel>>) {
        when(it) {
            is CharacterDetailViewModel.SeriesState.Loading-> {
                showLoading(true)
                binding.uiStateView.showLoading()
            }
            is CharacterDetailViewModel.SeriesState.Success -> {
                showLoading(false)
                refreshSeries(it.data)
                binding.uiStateView.dismiss()
            }
            is CharacterDetailViewModel.SeriesState.Error -> {
                showLoading(false)
                when(it.cause) {
                    NO_CONNECTION -> binding.uiStateView.showNoInternetConnection()
                    else -> binding.uiStateView.showError("Unknown error")
                }
            }
        }
    }

    private fun refreshData(character : MarvelCharacterDetail) {
        binding.character = character
    }

    private fun showLoading(isLoading :Boolean) {
        binding.isLoading = isLoading
    }

    private fun initRecyclerView() {
        adapter = CharacterSeriesRecyclerAdapter(this)
        val layoutManager = LinearLayoutManager(activity,  LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = adapter
    }

    private fun refreshSeries(series: List<MarvelCharacterSeriesUiModel>) {
        adapter.submitList(series)
    }

    override fun onSeriesPressed(character: MarvelCharacterSeriesUiModel) {

    }


}
