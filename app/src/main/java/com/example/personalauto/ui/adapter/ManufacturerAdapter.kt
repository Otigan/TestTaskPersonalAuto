package com.example.personalauto.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.personalauto.data.model.Manufacturer
import com.example.personalauto.databinding.ItemManufacturerBinding

class ManufacturerAdapter(private val onClick: (manufacturer: Manufacturer) -> Unit) :
    PagingDataAdapter<Manufacturer, ManufacturerAdapter.ManufacturerViewHolder>(COMPARATOR) {

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Manufacturer>() {
            override fun areItemsTheSame(oldItem: Manufacturer, newItem: Manufacturer) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Manufacturer, newItem: Manufacturer): Boolean =
                oldItem == newItem
        }
    }

    class ManufacturerViewHolder(
        private val binding: ItemManufacturerBinding,
        private val onClick: (manufacturer: Manufacturer) -> Unit
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(manufacturer: Manufacturer) {
            binding.apply {
                manufacturerName.text = manufacturer.name

                root.setOnClickListener {
                    onClick(manufacturer)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManufacturerViewHolder {
        val binding =
            ItemManufacturerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ManufacturerViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ManufacturerViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(it)
        }
    }
}
