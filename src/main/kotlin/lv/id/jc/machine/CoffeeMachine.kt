package lv.id.jc.machine

import lv.id.jc.machine.unit.ControlUnit
import lv.id.jc.machine.unit.StorageUnit
import java.util.function.Consumer
import java.util.function.Supplier

typealias InputUnit = Supplier<String>
typealias DisplayUnit = Consumer<String>

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