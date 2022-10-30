package lv.id.jc.machine.core

sealed interface Unit {
    fun status(): String
}