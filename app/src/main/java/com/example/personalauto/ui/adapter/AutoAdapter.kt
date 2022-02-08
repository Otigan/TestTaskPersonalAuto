package com.example.personalauto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.personalauto.data.model.Auto
import com.example.personalauto.databinding.ItemAutoBinding

class AutoAdapter(private val onClick: (auto: Auto) -> Unit) :
    PagingDataAdapter<Auto, AutoAdapter.AutoViewHolder>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Auto>() {
            override fun areItemsTheSame(oldItem: Auto, newItem: Auto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Auto, newItem: Auto): Boolean =
                oldItem == newItem
        }
    }

    class AutoViewHolder(
        private val binding: ItemAutoBinding,
        private val onClick: (auto: Auto) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(auto: Auto) {
            binding.apply {
                autoName.text = auto.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AutoViewHolder {
        val binding = ItemAutoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AutoViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: AutoViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }
}