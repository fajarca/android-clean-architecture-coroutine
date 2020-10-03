package io.fajarca.news_category.presentation


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import io.fajarca.news_category.R
import io.fajarca.news_category.databinding.FragmentNewsCategoryBinding
import io.fajarca.news_category.presentation.adapter.NewsCategoryRecyclerAdapter
import io.fajarca.news_category.presentation.model.NewsCategory

/**
 * A simple [Fragment] subclass.
 */
class NewsCategoryFragment : Fragment(), NewsCategoryRecyclerAdapter.NewsCategoryClickListener {

    private lateinit var binding : FragmentNewsCategoryBinding
    private val appBarConfiguration by lazy { AppBarConfiguration.Builder(R.id.fragmentNewsCategory).build() }
    private lateinit var adapter: NewsCategoryRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_news_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initRecyclerView()
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.includedToolbar.toolbar)
        binding.includedToolbar.toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun initRecyclerView() {
        adapter =
            NewsCategoryRecyclerAdapter(this)
        val layoutManager = GridLayoutManager(requireActivity(), 4)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.adapter = adapter
        adapter.submitList(populateCategories())
    }

    override fun onNewsCategoryPressed(category: NewsCategory) {
        val action =
            NewsCategoryFragmentDirections.actionFragmentNewsCategoryToFragmentNews(
                null,
                category.id,
                category.name
            )
        findNavController().navigate(action)
    }

    private fun populateCategories(): List<NewsCategory> {
        val categories = mutableListOf<NewsCategory>()
        categories.add(
            NewsCategory(
                "business",
                "Business",
                R.drawable.ic_business
            )
        )
        categories.add(
            NewsCategory(
                "technology",
                "Technology",
                R.drawable.ic_technology
            )
        )
        categories.add(
            NewsCategory(
                "entertainment",
                "Entertainment",
                R.drawable.ic_entertainment
            )
        )
        categories.add(
            NewsCategory(
                "general",
                "General",
                R.drawable.ic_general
            )
        )
        categories.add(
            NewsCategory(
                "health",
                "Health",
                R.drawable.ic_health
            )
        )
        categories.add(
            NewsCategory(
                "science",
                "Science",
                R.drawable.ic_science
            )
        )
        categories.add(
            NewsCategory(
                "sports",
                "Sport",
                R.drawable.ic_sports
            )
        )
        return categories
    }

}
