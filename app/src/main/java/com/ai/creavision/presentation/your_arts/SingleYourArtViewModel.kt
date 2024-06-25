package com.ai.creavision.presentation.your_arts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai.creavision.domain.model.Favorite
import com.ai.creavision.domain.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SingleYourArtViewModel @Inject constructor(
    private val repository: RepositoryInterface
): ViewModel() {

    val liveDataAddResult = MutableLiveData<Long>()
    val liveDataDeleteResult = MutableLiveData<Int>()
    val liveDataIsExists = MutableLiveData<Boolean>()

    fun addFavorite(favorite: Favorite) {

        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)
        }

        viewModelScope.launch(handler) {
            val response = repository.addFavorite(favorite)
            liveDataAddResult.value = response
        }
    }

    fun deleteFavorite(imgPath: String) {



        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)
        }

        viewModelScope.launch(handler) {
            val file = File(imgPath)
            file.delete()

            val response  = repository.deleteFavorite(imgPath)
            liveDataDeleteResult.value = response
        }
    }

    fun isFavoriteExists(imgPath: String) {
        var response = false
        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)
        }

        viewModelScope.launch(handler) {
            val response  = repository.isFavoriteExists(imgPath)
            liveDataIsExists.value = response
        }
    }
}