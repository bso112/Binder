package com.bso112.binder

@JvmInline
value class Id(val value: String) {
    constructor(id: Int) : this(id.toString())
}

fun Int.toId() = Id(this)
fun String.toId() = Id(this)
