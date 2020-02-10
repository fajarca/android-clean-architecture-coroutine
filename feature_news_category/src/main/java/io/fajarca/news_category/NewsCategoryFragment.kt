package io.fajarca.news_category


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.news_category.databinding.FragmentNewsCategoryBinding
import io.fajarca.presentation.extension.navigateTo

/**
 * A simple [Fragment] subclass.
 */
class NewsCategoryFragment : Fragment(), NewsCategoryRecyclerAdapter.NewsCategoryClickListener {

    private lateinit var binding : FragmentNewsCategoryBinding
    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.fragmentNewsCategory).build() }
    private lateinit var adapter: NewsCategoryRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.contentToolbar.toolbar)
        binding.contentToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun initRecyclerView() {
        adapter = NewsCategoryRecyclerAdapter(this)
        val layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = adapter
        adapter.submitList(populateCategories())
    }

    override fun onNewsCategoryPressed(category: NewsCategory) {
        navigateTo("app://home/null/${category.id}")
    }

    private fun populateCategories(): List<NewsCategory> {
        val categories = mutableListOf<NewsCategory>()
        categories.add(NewsCategory("business", "Business" ))
        categories.add(NewsCategory("technology", "Technology" ))
        categories.add(NewsCategory("entertainment", "Entertainment" ))
        categories.add(NewsCategory("general", "General" ))
        categories.add(NewsCategory("health", "Health" ))
        categories.add(NewsCategory("science", "Science" ))
        categories.add(NewsCategory("sports", "Sport" ))
        return categories
    }

}
