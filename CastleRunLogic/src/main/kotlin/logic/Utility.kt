package org.example.logic

import com.google.gson.Gson

fun Any.toJson(): String = Gson().toJson(this)  //value.toJson() returns the JSON string
inline fun <reified T> String.toObject(): T = Gson().fromJson(this, T::class.java)  //string.fromJson<Type>()

fun random(min: Int, max: Int): Int = (min..max).random() //random(min, max) returns a random number between min and max

fun main() {
    //test the probability of the numbers from 1 to 5 by running the random function 100000 times
    val results = mutableMapOf<Int, Int>()
    repeat(100000) {
        val r = random(1, 5)
        results[r] = results.getOrDefault(r, 0) + 1
    }
    println(results)
}

