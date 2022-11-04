package lv.id.jc.machine

import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.InputUnit
import lv.id.jc.machine.unit.impl.StorageBlock

fun main() {
    val coffeeMachine = filledCoffeeMachine()

    coffeeMachine.powerOn()

    while (coffeeMachine.isOperate()) {
        coffeeMachine.processRequest()
    }
}

fun filledCoffeeMachine(): CoffeeMachine {
    val inputUnit = InputUnit { readln() }
    val displayUnit = DisplayUnit { text -> println(text) }

    val storageUnit = StorageBlock()

    with(storageUnit) {
        fill(Resource.Water, 400)
        fill(Resource.Milk, 540)
        fill(Resource.CoffeeBeans, 120)
        fill(Resource.DisposableCups, 9)
        fill(Resource.Cash, 550)
    }

    val controlUnit = ControlBlock(displayUnit, storageUnit)

    return CoffeeMachine(inputUnit, controlUnit)
}
