package lv.id.jc.machine.core

class MoneyUnit(private var money: Int) : Unit {

    override fun status() = "\$$money of money"
}