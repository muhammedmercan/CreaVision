package com.ai.creavision.presentation.your_arts


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ai.creavision.databinding.ItemArtBinding
import com.ai.creavision.domain.model.Favorite
import com.squareup.picasso.Picasso
import javax.inject.Inject


class YourArtsAdapter @Inject constructor(

) : RecyclerView.Adapter<YourArtsAdapter.ViewHolder>() {

    private var onItemClickListener : ((Favorite) -> Unit)? = null


    class ViewHolder(val binding: ItemArtBinding) : RecyclerView.ViewHolder(binding.root)


    private val diffUtil = object : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.imgUrl == newItem.imgUrl
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var photoFiles: List<Favorite>?
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArtBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(listener : (Favorite) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        println(photoFiles?.get(position)?.imgUrl)

        //holder.binding.imageView.setImageResource(R.drawable.victorian)

        Picasso.get().load(photoFiles?.get(position)?.imgUrl!!).into(holder.binding.imageView);
/*
        Glide.with(holder.binding.imageView)
            .load(R.drawable.ic_launcher_foreground)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            //.thumbnail(0.1f)
            //.override(500,500)
            //.encodeQuality(30)
            //.placeholder(R.drawable.ic_launcher_background)
            //.skipMemoryCache(true)
            .into(holder.binding.imageView)

 */
        holder.itemView.setOnClickListener() {
            onItemClickListener?.let {
                it(Favorite(photoFiles?.get(position)?.imgUrl!!, photoFiles?.get(position)?.prompt!!))
                //it(photoFiles?.get(position)?.imgUrl!!)
                //it(photoFiles?.get(position)?.imgUrl!!)
            }
        }
    }

    override fun getItemCount(): Int {
        return photoFiles!!.size
    }
}