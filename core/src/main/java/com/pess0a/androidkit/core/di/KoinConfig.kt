package com.pess0a.androidkit.core.di

import android.app.Application

internal data class KoinConfig (
    val application : Application,
    val baseUrl: String,
    val isDebuggable: Boolean
)