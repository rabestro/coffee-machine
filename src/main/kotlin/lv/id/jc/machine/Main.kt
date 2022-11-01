package lv.id.jc.machine

import lv.id.jc.machine.unit.CoffeeMachine
import lv.id.jc.machine.state.Menu
import lv.id.jc.machine.unit.ResourcesUnit

fun main() {
    println("Hello, World!")

    val machine = createCoffeeMachine()

    while (machine.isOperate()) {
        println(machine.prompt())
        machine.process(readln())
        println(machine.output())
    }
}

fun createCoffeeMachine(): CoffeeMachine {
    val resourcesUnit = ResourcesUnit()
    val menu = Menu(resourcesUnit)
    return CoffeeMachine(menu)
}