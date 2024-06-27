package com.ai.creavision.presentation.your_arts

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai.creavision.domain.model.Favorite
import com.ai.creavision.domain.repository.RepositoryInterface
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
    @ApplicationContext private val context: Context,
    private val repository: RepositoryInterface

): ViewModel() {

    val liveData = MutableLiveData<List<Favorite>?>()
    var fromHome : Boolean = false

    fun getImages() {

        viewModelScope.launch() {
        liveData.value = repository.getAllFavorites()


    }}

    fun reset() {
        liveData.value = null
    }
}