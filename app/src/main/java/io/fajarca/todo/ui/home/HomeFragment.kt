package io.fajarca.todo.ui.home

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import io.fajarca.todo.R
import io.fajarca.todo.base.BaseFragment
import io.fajarca.todo.data.local.entity.Todo
import io.fajarca.todo.data.remote.response.NowPlayingResponse
import io.fajarca.todo.databinding.FragmentHomeBinding
import io.fajarca.todo.vo.Result
import io.fajarca.todo.vo.ValidationResult
import kotlinx.android.synthetic.main.fragment_home.*
import timber.log.Timber

class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>(),
    TodoRecyclerAdapter.TodoClickListener {

    override fun getLayoutResourceId() = R.layout.fragment_home
    override fun getViewModelClass() = HomeViewModel::class.java
    private lateinit var adapter : TodoRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()

        btnSave.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()

            vm.validateInput(title, description)
        }

        vm.getTodo()
        vm.getAllNowPlaying()

        vm.insertTodo.observe(this, Observer { subscribeInsertTodo(it) })
        vm.todo.observe(this, Observer { subscribeTodo(it) })
        vm.nowPlaying.observe(this, Observer { subscribeNowPlaying(it) })

    }

    private fun initRecyclerView() {
        adapter = TodoRecyclerAdapter(this)
        val layoutManager = LinearLayoutManager(activity)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter
    }

    private fun refreshData(todos: List<Todo>) {
        adapter.submitList(todos)
    }

    private fun subscribeInsertTodo(result: ValidationResult?) {
        when (result) {
            is ValidationResult.VALID -> {
                longSnackbar("Valid")
            }
            is ValidationResult.INVALID -> {
                longSnackbar(result.errorMessage)
            }
        }
    }

    private fun subscribeTodo(result: Result<List<Todo>>) {
        when (result.status) {
            Result.Status.LOADING -> {
                Timber.v("Status : Loading")
            }
            Result.Status.SUCCESS -> {
                Timber.v("Status : Success. Data size ${result.data?.size}")
                refreshData(result.data ?: emptyList())
            }
            Result.Status.ERROR -> {
                Timber.v("Status : Error ${result.message}" )
            }
        }
    }

    private fun subscribeNowPlaying(it: Result<NowPlayingResponse>) {
        when(it.status) {
            Result.Status.LOADING -> {
                Timber.v("Status : Loading")
            }
            Result.Status.SUCCESS -> {
                Timber.v("Status : Success")
            }
            Result.Status.ERROR -> {
                Timber.v("Status : Error" )
            }
        }
    }

    override fun onTodoPressed() {

    }

}
