package com.ai.creavision.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.adapty.models.AdaptyPaywall

object DataHolder {

    var currentGlideCache : Boolean = false

    var paywall = MutableLiveData<AdaptyPaywall?>()

    var isPremium = MutableLiveData<Boolean>()
}