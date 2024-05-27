package com.bangkit2024.huetiful.ui.fragments.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bangkit2024.huetiful.databinding.ItemResultBinding

class ResultAdapter : RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val binding = ItemResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultViewHolder(binding)
    }

    override fun getItemCount(): Int = 10

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {

    }

    class ResultViewHolder(binding: ItemResultBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}