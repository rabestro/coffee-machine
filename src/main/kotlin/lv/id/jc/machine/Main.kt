package lv.id.jc.machine

import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.state.*
import lv.id.jc.machine.unit.CoffeeMachine
import lv.id.jc.machine.unit.Engine

fun main() {
    val machine = createCoffeeMachine()

    while (machine.isOperate()) {
        machine.prompt()
        machine.process(readln())
    }
}

fun createCoffeeMachine(): CoffeeMachine {
    val engine = Engine()

    with(engine) {
        fill(Resource.Water, 400)
        fill(Resource.Milk, 540)
        fill(Resource.CoffeeBeans, 120)
        fill(Resource.DisposableCups, 9)
        fill(Resource.Money, 550)
    }

    val states = mapOf(
        State.MainMenu to MainMenu(engine),
        State.FillWater to FillResource(engine, Resource.Water, State.FillMilk),
        State.FillMilk to FillResource(engine, Resource.Milk, State.FillBeans),
        State.FillBeans to FillResource(engine, Resource.CoffeeBeans, State.FillCups),
        State.FillCups to FillResource(engine, Resource.DisposableCups, State.MainMenu)
    )

    return CoffeeMachine(engine, states)
}
