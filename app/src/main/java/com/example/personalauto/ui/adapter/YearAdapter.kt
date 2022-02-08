package com.example.personalauto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.personalauto.data.model.Year
import com.example.personalauto.databinding.ItemYearBinding

class YearAdapter(private val onClick: (year: Year) -> Unit) :
    ListAdapter<Year, YearAdapter.YearViewHolder>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Year>() {
            override fun areItemsTheSame(oldItem: Year, newItem: Year): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Year, newItem: Year): Boolean =
                oldItem == newItem
        }
    }

    class YearViewHolder(
        private val binding: ItemYearBinding,
        private val onClick: (year: Year) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(year: Year) {
            binding.apply {
                root.setOnClickListener {
                    onClick(year)
                }
                yearAuto.text = year.year
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YearViewHolder {
        val binding = ItemYearBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return YearViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: YearViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }
}