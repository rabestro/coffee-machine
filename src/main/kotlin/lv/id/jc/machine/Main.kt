package lv.id.jc.machine

import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.ControlBlock
import lv.id.jc.machine.unit.StorageBlock

fun main() {

    val storage = StorageBlock()

    with(storage) {
        fill(Resource.Water, 400)
        fill(Resource.Milk, 540)
        fill(Resource.CoffeeBeans, 120)
        fill(Resource.DisposableCups, 9)
        fill(Resource.Cash, 550)
    }

    val controlUnit = ControlBlock(::println, storage)

    val coffeeMachine = CoffeeMachine(::readln, ::println, storage, controlUnit)


}

