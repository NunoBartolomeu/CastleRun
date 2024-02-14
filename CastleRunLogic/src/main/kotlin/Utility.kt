package org.example

import com.google.gson.Gson

fun Any.toJson(): String = Gson().toJson(this)  //value.toJson() returns the JSON string
inline fun <reified T> String.toObject(): T = Gson().fromJson(this, T::class.java)  //string.fromJson<Type>()

