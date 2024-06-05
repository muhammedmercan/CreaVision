package com.ai.creavision.presentation.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ai.creavision.domain.model.ImageResponse
import com.ai.creavision.domain.model.PromptRequest
import com.ai.creavision.domain.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllResultsViewModel @Inject constructor(
    private val repository: RepositoryInterface

): ViewModel() {

    val liveData = MutableLiveData<ImageResponse?>()


    fun createImage(createImageModel: PromptRequest) {

        val handler = CoroutineExceptionHandler {context, throwable ->
            println(throwable)

        }

        viewModelScope.launch(handler) {
            val response = repository.createImage(createImageModel)

            if(response.isSuccessful) {
                response.body()?.let {

                    val predictionId = it.urls?.get?.substringAfter("predictions/")

                    while (true) {
                        var update = repository.getUpdate(predictionId!!)
                        if (update.body()?.status == "succeeded" || update.body()?.status == "failed" ||  update.body()?.status == "canceled") {
                            liveData.value = update.body()
                            break
                        }
                        delay(2000)
                    }
                }
            }
        }
    }


    fun reset() {
        liveData.value = null
    }
}