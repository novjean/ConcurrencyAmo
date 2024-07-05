package com.novatech.concurrencyamo

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.novatech.concurrencyamo.ui.theme.ConcurrencyAmoTheme
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private var TAG: String = "Coroutine"

//    private val scope = CoroutineScope(Dispatchers.IO + CoroutineName("MyScope"))
    private val scope = CoroutineScope(CoroutineName("MyScope"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val job = scope.launch {
//            Log.d("Coroutine", this.coroutineContext.toString())
//            launch {
//                Log.d("Coroutine", this.coroutineContext.toString())
//            }
//        }

//        lifecycleScope.launch {
//            while(true){
//                delay(1000L)
//                Log.d("Coroutine", "running...")
//
//            }
//        }

        setContent {
            Navigation()
        }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        val mainJob = scope.launch {
            val job1 = launch{
                while(true){
//                    ensureActive()
//                    Log.d("Coroutine", "Job 1 is running")
                    delay(100L)
                }
            }

            val job2 = launch {
//                Log.d("Coroutine", "Job 2 is running")
            }

            delay(1000L)
//            Log.d("Coroutine", "Job 2 is canceling")
//            job1.cancel()
//            job1.join()
            job2.cancelAndJoin()
//            Log.d(TAG, "Job 2 is CANCELED")

        }

        runBlocking {
            delay(2000L)
//            Log.d("Coroutine", "Canceling...")

            mainJob.cancelAndJoin()
//            Log.d(TAG, "Main job canceled")
        }

//        runBlocking {
//            GlobalScope.launch {
//                Log.d(TAG, "Coroutine 1")
//                delay(5000L)
//            }
//
//            GlobalScope.launch {
//                Log.d(TAG, "Coroutine 2")
//                delay(5000L)
//
//            }
//        }
//
//        Log.d(TAG, "runBlocking completed")


        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, this.coroutineContext.toString())

            withContext(Dispatchers.Main) {
                Log.d(TAG, this.coroutineContext.toString())

            }
        }



        return super.onCreateView(name, context, attrs)
    }
}