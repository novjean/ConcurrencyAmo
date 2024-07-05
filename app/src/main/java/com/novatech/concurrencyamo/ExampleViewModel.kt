package com.novatech.concurrencyamo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class ExampleViewModel: ViewModel() {
    val TAG: String = "ExampleViewModel"

    init {
//        viewModelScope.launch {
//            val result = withContext(Dispatchers.IO) {
//                delay(3000)
//                true
//            }
//            // will receive a result of true in three seconds
//            Log.d(TAG, "$result")
//        }

        val result = viewModelScope.async {
            delay(3000)
            true
        }
        result.invokeOnCompletion {
            if(it==null){
                Log.d(TAG, "${result.getCompleted()}")
            }
        }
    }
}