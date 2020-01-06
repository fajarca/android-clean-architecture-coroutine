package io.fajarca.feature.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.feature.R
import io.fajarca.feature.ui.BaseFragment
import io.fajarca.feature.databinding.FragmentHomeBinding
import io.fajarca.core.database.Character
import io.fajarca.feature.di.DaggerFeatureComponent
import javax.inject.Inject

class HomeFragment : Fragment(),
    CharactersRecyclerAdapter.CharacterClickListener {

    
    @Inject
    lateinit var vm : HomeViewModel
    
    private lateinit var adapter : CharactersRecyclerAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFeatureComponent.builder().build().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.lifecycleOwner = this
        binding.executePendingBindings()
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        vm.getAllCharacters()

        vm.characters.observe(viewLifecycleOwner, Observer { subscribeCharacters(it) })

    }

    private fun subscribeCharacters(it: HomeViewModel.Result<List<Character>>) {
        when(it) {
            is HomeViewModel.Result.Loading -> {
                showLoading(true)
            }
            is HomeViewModel.Result.Success -> {
                showLoading(false)
                refreshData(it.data)
            }
            is HomeViewModel.Result.Error -> {
                showLoading(false)
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

    private fun refreshData(characters: List<Character>) {
        adapter.submitList(characters)
    }


    override fun onCharacterPressed() {

    }

    private fun showLoading(isLoading : Boolean) {
        binding.isLoading = isLoading
    }

}
