package com.nobowl.borderfix

class PlayerStats {

    private var blocked = 0
    private var pearl = 0
    private var outside = 0
    private var aote = 0

    fun add(type: String) {
        when (type) {
            "BLOCKED" -> blocked++
            "PEARL" -> pearl++
            "OUTSIDE" -> outside++
            "AOTE_ATTEMPT" -> aote++
        }
    }

    fun summary(): String {
        return "Blocked:$blocked Pearl:$pearl Outside:$outside AOTE:$aote"
    }
}