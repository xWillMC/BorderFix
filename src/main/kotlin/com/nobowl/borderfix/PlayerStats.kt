package com.nobowl.borderfix

class PlayerStats {

    var blocked = 0
        private set
    var pearl = 0
        private set
    var outside = 0
        private set
    var aote = 0
        private set

    fun add(type: String) {
        when (type) {
            "BLOCKED" -> blocked++
            "PEARL"   -> pearl++
            "OUTSIDE" -> outside++
            "AOTE_ATTEMPT" -> aote++
        }
    }

    fun summary(): String =
        "Blocked:$blocked Pearl:$pearl Outside:$outside AOTE:$aote"
}
