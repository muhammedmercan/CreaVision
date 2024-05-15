package com.ai.creavision.presentation.create

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai.creavision.domain.model.CreateImageModel
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.repository.RepositoryInterface
import com.ai.creavision.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val repository: RepositoryInterface

): ViewModel() {

    val liveData = MutableLiveData<ImageResponse>()


    fun createImage(createImageModel: CreateImageModel) {

        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)

        }

        viewModelScope.launch(handler) {
            val response = repository.createImage(createImageModel)

            if(response.isSuccessful) {
                response.body()?.let {
                    liveData.value = it
                }
            }
        }
    }
}