package com.cameralibrary

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.livefront.bridge.Bridge

class PVNavHostFragment: NavHostFragment() {

    override fun onSaveInstanceState(outState: Bundle) {
        Bridge.saveInstanceState(this, outState)
        outState.clear()
        super.onSaveInstanceState(Bundle())
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        Bridge.restoreInstanceState(this, savedInstanceState)
        savedInstanceState?.clear()
        super.onViewStateRestored(savedInstanceState)
    }
}