package com.ai.creavision.utils

import com.adapty.models.AdaptyPaywall

object DataHolder {

    var currentGlideCache : Boolean = false

    lateinit var paywall : AdaptyPaywall

    var isPremium : Boolean = false
}