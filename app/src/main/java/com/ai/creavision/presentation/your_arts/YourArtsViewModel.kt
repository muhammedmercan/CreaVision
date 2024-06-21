package com.ai.creavision.presentation.your_arts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai.creavision.utils.DataHolder
import com.bumptech.glide.Glide
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class YourArtsViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

    val liveData = MutableLiveData<List<File>?>()

    var fromHome : Boolean = false


    /*
    init {
        getImages()
    }

     */


    fun getImages() {

        viewModelScope.launch() {
/*
        val photoCacheDir = Glide.getPhotoCacheDir(context)
        val list = photoCacheDir?.listFiles()?.toMutableList()
        list?.removeAt(0)
        DataModel.imageFiles = list
        liveData.value = Glide.getPhotoCacheDir(context)?.listFiles()?.toMutableList()

 */
        //TODO burası düzeltilcek
        liveData.value = DataModel.deneme

    }}

    fun reset() {
        liveData.value = null
    }

}