package com.novatech.concurrencyamo

import kotlinx.coroutines.*

class Concurrency {
}

fun main() {
    GlobalScope.launch {
        task2()
    }
    task1()
    Thread.sleep(2000L)

}

fun task1(){
    print("hello ")
    print(Thread.currentThread().name)
}

suspend fun task2(){
    withContext(Dispatchers.IO){
        delay(1000L)
        print("world")
        print(Thread.currentThread().name)
    }
}