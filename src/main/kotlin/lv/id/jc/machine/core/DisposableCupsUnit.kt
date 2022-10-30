package lv.id.jc.machine.core

class DisposableCupsUnit(private var cups: Int = 0) : Unit {
    override fun status() = "$cups disposable cups"
}