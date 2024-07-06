package com.novatech.concurrencyamo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.Channel.Factory.CONFLATED
import kotlinx.coroutines.channels.Channel.Factory.UNLIMITED
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
class MainViewModel : ViewModel() {
    private val TAG: String = "MainViewModel"
//    private var channel = Channel<Language>()
    private var channel: ReceiveChannel<Language> = Channel()

    init {
        // Coroutine #1
        // this will be used as producer
        viewModelScope.launch{
            channel = produce(capacity = UNLIMITED) {
                send(Language.Kotlin)
                send(Language.Java)
                send(Language.Python)
                send(Language.Javascript)
            }

//            channel = produce {
//                send(Language.Kotlin)
//                send(Language.Java)
//            }

//            Log.d(TAG, "Kotlin sent!")
//            channel.send(Language.Kotlin)
//            Log.d(TAG, "Java sent!")
//            channel.send(Language.Java)
//            channel.close()
        }
        // Coroutine #2
        // will be used to recieve
        viewModelScope.launch {
            channel.consumeEach {
                // used to avoid calling receive multiple times
                Log.d(TAG, it.name)
            }
            Log.d(TAG, "${channel.isClosedForReceive}")


//            Log.d(TAG, "${channel.isClosedForReceive}")
//            Log.d(TAG, "${channel.receive()}")
//            Log.d(TAG, "${channel.receive()}")
//            Log.d(TAG, "${channel.isClosedForReceive}")
        }
    }
}

enum class Language {
    Kotlin,
    Java,
    Python,
    Javascript
}