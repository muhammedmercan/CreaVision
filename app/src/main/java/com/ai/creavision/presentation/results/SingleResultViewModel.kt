package com.ai.creavision.presentation.results

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
class SingleResultViewModel @Inject constructor(
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

    fun deleteFavorite(imgUrl: String) {

        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)
        }

        viewModelScope.launch(handler) {

            val response  = repository.deleteFavorite(imgUrl)
            liveDataDeleteResult.value = response
        }
    }

    fun deleteProductWithUrl(imgUrl: String) {



        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)
        }

        viewModelScope.launch(handler) {

            val file = File(repository.getImgPathWithUrl(imgUrl))
            file.delete()

            val response  = repository.deleteProductWithUrl(imgUrl)
            liveDataDeleteResult.value = response
        }
    }

    fun isFavoriteExists(imgPath: String) {

        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)
        }

        viewModelScope.launch(handler) {

            val response  = repository.isFavoriteExists(imgPath)
            liveDataIsExists.value = response
        }
    }

    fun isFavoriteExistsWithUrl(imgUrl: String) {

        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)
        }

        viewModelScope.launch(handler) {

            val response  = repository.isFavoriteExistsWithUrl(imgUrl)
            liveDataIsExists.value = response
        }
    }
}