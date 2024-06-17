package com.ai.creavision.presentation.your_arts

import androidx.recyclerview.widget.DiffUtil
import java.io.File

class AdapterDiffCallback(private val oldList: List<File>, private val newList: List<File>) : DiffUtil.Callback() {


    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].absolutePath == newList[newItemPosition].absolutePath
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}