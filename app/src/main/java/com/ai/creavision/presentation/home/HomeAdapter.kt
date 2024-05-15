package com.ai.creavision.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ai.creavision.R
import com.ai.creavision.databinding.ItemArtStyleBinding
import com.ai.creavision.domain.model.ArtStyle
import com.ai.creavision.domain.model.Style
import javax.inject.Inject

class HomeAdapter @Inject constructor() : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    private var onItemClickListener : ((Int) -> Unit)? = null

    class ViewHolder(val binding: ItemArtStyleBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<ArtStyle>() {
        override fun areItemsTheSame(oldItem: ArtStyle, newItem: ArtStyle): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArtStyle, newItem: ArtStyle): Boolean {

            return  oldItem == newItem

        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var artyStyleResponseList: List<ArtStyle>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArtStyleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(listener : (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        println(Style.getValue() + "dsadsadsa   " + artyStyleResponseList[position].name)

        if (Style.getValue() == artyStyleResponseList[position].name) {
            holder.binding.imgArtStyle.strokeWidth = 5F
            holder.binding.txtArtStyle.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))

        } else {
            holder.binding.imgArtStyle.strokeWidth = 0F
            holder.binding.txtArtStyle.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
        }


        holder.binding.imgArtStyle.setImageResource(artyStyleResponseList[position].image)
        holder.binding.txtArtStyle.text = artyStyleResponseList[position].name




        holder.itemView.setOnClickListener() {
            Style.setValue(holder.binding.txtArtStyle.text.toString())

            holder.binding.imgArtStyle.strokeWidth = 5F
            holder.binding.txtArtStyle.setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))

        }



    }

    override fun getItemCount(): Int {
        return artyStyleResponseList.size
    }
}