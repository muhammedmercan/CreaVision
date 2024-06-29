package com.ai.creavision.presentation.your_arts

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ai.creavision.databinding.ItemArtBinding
import com.ai.creavision.domain.model.Favorite
import java.io.ByteArrayOutputStream
import java.io.File
import javax.inject.Inject

class YourArtsAdapter @Inject constructor() : RecyclerView.Adapter<YourArtsAdapter.ViewHolder>() {

    private var onItemClickListener: ((Favorite) -> Unit)? = null

    class ViewHolder(val binding: ItemArtBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object : DiffUtil.ItemCallback<Favorite>() {
        override fun areItemsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Favorite, newItem: Favorite): Boolean {
            return oldItem.imgPath == newItem.imgPath
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    var photoFiles: List<Favorite>?
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemArtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    fun setOnItemClickListener(listener: (Favorite) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        println(photoFiles?.get(position)?.imgUrl)

        //holder.binding.imageView.setImageResource(R.drawable.victorian)

        var bitmap: Bitmap? = null

        try {
            val file = File(photoFiles?.get(position)?.imgPath)
            bitmap = BitmapFactory.decodeFile(file.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }

        //Bitmap boyutunu küçültmek için
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        val compressedByteArray = outputStream.toByteArray()
        val compressedBitmap =
            BitmapFactory.decodeByteArray(compressedByteArray, 0, compressedByteArray.size)


        holder.binding.imageView.setImageBitmap(compressedBitmap)
        bitmap?.recycle()

        holder.itemView.setOnClickListener() {
            onItemClickListener?.let {
                it(
                    Favorite(
                        photoFiles?.get(position)?.imgUrl!!,
                        photoFiles?.get(position)?.prompt!!,
                        photoFiles?.get(position)?.imgPath!!
                    )
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return photoFiles!!.size
    }


}