package com.cameralibrary

import android.content.Context
import androidx.lifecycle.ViewModel
import com.camera.data.models.UserProfile
import com.camera.domain.syncHelper.SyncHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FirstScreenFragmentViewModel @Inject constructor (
    private val syncHelper: SyncHelper
) : ViewModel() {

    fun syncData(userProfile: UserProfile, c: Context){
        CoroutineScope(Dispatchers.IO).launch {
            syncHelper.syncPendingData(userProfile, c)
        }
    }

}

