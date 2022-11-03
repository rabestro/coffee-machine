package lv.id.jc.machine

import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.ControlUnit
import lv.id.jc.machine.unit.InputUnit

class CoffeeMachine(
    private val inputUnit: InputUnit,
    private val controlUnit: ControlUnit
) : VendingMachine {

    override fun powerOn() = controlUnit.switchTo(ControlState.MainMenu)

    override fun isOperate(): Boolean = controlUnit.isOperate()

    override fun processRequest() = controlUnit.process(inputUnit.get())
}