package io.fajarca.todo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.fajarca.todo.data.local.entity.Todo
import io.fajarca.todo.databinding.ItemTodoBinding

class TodoRecyclerAdapter(private val listener: TodoClickListener) : ListAdapter<Todo, TodoRecyclerAdapter.TodoViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodoBinding.inflate(layoutInflater, parent,false)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
    }


    inner class TodoViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(todo: Todo, listener: TodoClickListener) {
            binding.todo = todo
            binding.root.setOnClickListener { listener.onTodoPressed() }
            binding.executePendingBindings()
        }
    }

    interface TodoClickListener {
        fun onTodoPressed()
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Todo>() {
            override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
                return oldItem == newItem
            }

        }
    }
}