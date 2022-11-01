package lv.id.jc.machine.state

import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.ResourcesUnit

class Menu(private val resourcesUnit: ResourcesUnit) : State(
    "Write action (buy, fill, take, remaining, exit): "
) {
    private var nextState: State = this

    override fun next(): State {
        return nextState
    }

    override fun process(request: String) {
        nextState = this
        when (request) {
            "remaining" -> output = resourcesUnit.status()
            "take" -> {
                val money = resourcesUnit.volume(Resource.Money)
                resourcesUnit.take(Resource.Money, money)
                output = "I gave you \$$money"
            }
            "exit" -> {
                output = "Goodbye!"
                nextState = Exit
            }
        }
    }
}