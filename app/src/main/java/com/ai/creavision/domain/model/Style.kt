package com.ai.creavision.domain.model



object Style
{
    private var style: String? = null

    fun setValue(newStyle: String) {
        style = newStyle
    }

    fun getValue(): String? {
        return style
    }
}