package lv.id.jc.machine

import lv.id.jc.machine.unit.ControlUnit
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.InputUnit
import lv.id.jc.machine.unit.StorageUnit

class CoffeeMachine(
    private val inputUnit: InputUnit,
    private val displayUnit: DisplayUnit,
    private val storageUnit: StorageUnit,
    private val controlUnit: ControlUnit
) : VendingMachine {

    override fun powerOn() {
        TODO("Not yet implemented")
    }

    override fun isOperate(): Boolean {
        TODO("Not yet implemented")
    }

    override fun processRequest() {
        TODO("Not yet implemented")
    }
}