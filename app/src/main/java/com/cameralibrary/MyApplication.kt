package com.cameralibrary

import android.app.Application
import android.os.Bundle
import com.evernote.android.state.StateSaver
import com.livefront.bridge.Bridge
import com.livefront.bridge.SavedStateHandler
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val savedStateHandler = object: SavedStateHandler {
            override fun saveInstanceState(p0: Any, p1: Bundle) {
                StateSaver.saveInstanceState(p0, p1)
            }

            override fun restoreInstanceState(p0: Any, p1: Bundle?) {
                StateSaver.restoreInstanceState(p0, p1)
            }
        }
        Bridge.initialize(applicationContext, savedStateHandler)
    }
}
