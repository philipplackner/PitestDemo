package com.plcoding.pitestdemo

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform