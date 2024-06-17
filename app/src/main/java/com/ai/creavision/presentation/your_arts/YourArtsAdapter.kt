package com.ai.creavision.presentation.your_arts


import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ai.creavision.R
import com.ai.creavision.databinding.ItemArtBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.File
import javax.inject.Inject


class YourArtsAdapter @Inject constructor() : RecyclerView.Adapter<YourArtsAdapter.ViewHolder>() {

    private var onItemClickListener : ((String) -> Unit)? = null



    private val diffUtil = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var photoFiles: List<String>?
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)




    class ViewHolder(val binding: ItemArtBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArtBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(listener : (String) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        println(photoFiles?.get(position))


        Glide.with(holder.itemView.context)
            .load(photoFiles?.get(position))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .thumbnail(0.1f)
            //.override(1024,1024)
            //.encodeQuality(30)
            //.placeholder(R.drawable.ic_launcher_background)
            //.skipMemoryCache(true)
            .into(holder.binding.imageView)

        holder.itemView.setOnClickListener() {
            onItemClickListener?.let {
                it(photoFiles?.get(position)!!)
            }
        }

    }


    override fun getItemCount(): Int {
        return photoFiles!!.size
    }
}