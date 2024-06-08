package com.ai.creavision.presentation.old_results


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ai.creavision.databinding.ItemArtBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.io.File
import javax.inject.Inject


class YourArtsAdapter @Inject constructor() : RecyclerView.Adapter<YourArtsAdapter.ViewHolder>() {

    private var onItemClickListener : ((Int) -> Unit)? = null


    private val diffUtil = object : DiffUtil.ItemCallback<File>() {
        override fun areItemsTheSame(oldItem: File, newItem: File): Boolean {
            return oldItem == newItem
        }
        override fun areContentsTheSame(oldItem: File, newItem: File): Boolean {
            return  oldItem == newItem
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var photoFiles: List<File>?
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)





    class ViewHolder(val binding: ItemArtBinding) : RecyclerView.ViewHolder(binding.root)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArtBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(listener : (Int) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        Glide.with(holder.itemView)
            .load(photoFiles?.get(position))
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(true)
            .into(holder.binding.imageView)

        holder.itemView.setOnClickListener() {
            onItemClickListener?.let {
            }
        }

    }

    override fun getItemCount(): Int {
        return photoFiles!!.size
    }
}