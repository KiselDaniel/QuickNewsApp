package com.dado.utils

import android.content.Context

interface NetworkCheckerI {
    fun isInternetConnected(context: Context): Boolean
}