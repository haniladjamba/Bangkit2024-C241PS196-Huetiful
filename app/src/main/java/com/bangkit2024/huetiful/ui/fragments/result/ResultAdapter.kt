package com.bangkit2024.huetiful.ui.fragments.result

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.huetiful.data.local.model.PalateModel
import com.bangkit2024.huetiful.databinding.ItemResultBinding
import com.bangkit2024.huetiful.ui.fragments.NavigationController

class ResultAdapter(private val navController: NavigationController) : ListAdapter<PalateModel, ResultAdapter.ResultViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun onItemClick(view: View) {
        navController.navigatToFullDetailFragment(view)
    }

    inner class ResultViewHolder(binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

        private val firstItem = binding.vItem1
        private val secondItem = binding.vItem2
        private val thirdItem = binding.vItem3
        private val fourthItem = binding.vItem4

        fun bind(data: PalateModel)  {
            firstItem.setBackgroundColor(Color.parseColor(data.color1))
            secondItem.setBackgroundColor(Color.parseColor(data.color2))
            thirdItem.setBackgroundColor(Color.parseColor(data.color3))
            fourthItem.setBackgroundColor(Color.parseColor(data.color4))

            itemView.setOnClickListener {
                onItemClick(itemView)
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PalateModel>() {
            override fun areItemsTheSame(
                oldItem: PalateModel,
                newItem: PalateModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: PalateModel,
                newItem: PalateModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}