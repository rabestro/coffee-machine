package lv.id.jc.machine.state

import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.Engine

class MainMenu(private val engine: Engine) : Command {

    override fun process(request: String) {
        when (request) {
            "buy" -> engine.state = State.BuyCoffee
            "fill" -> engine.state = State.FillWater
            "take" -> {
                val money = engine.volume(Resource.Money)
                engine.take(Resource.Money, money)
                engine.display("I gave you \$$money")
            }
            "remaining" -> engine.display(status())
            "exit" -> engine.state = State.Exit
        }
    }

    private fun status() = Resource.values()
        .joinToString("\n", "The coffee machine has:\n")
        { it.status(engine.volume(it)) }
}