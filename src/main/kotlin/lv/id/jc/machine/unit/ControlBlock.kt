package lv.id.jc.machine.unit

import lv.id.jc.machine.DisplayUnit
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.model.ControlState.*
import lv.id.jc.machine.model.Resource

class ControlBlock(
    private val display: DisplayUnit,
    private val storage: StorageUnit
) : ControlUnit {
    private var controlState = Shutdown

    override fun process(request: String) {
        when (controlState) {
            MainMenu -> ::mainMenu
            FillWater -> { volume -> fill(Resource.Water, volume, FillMilk) }
            FillMilk -> { volume -> fill(Resource.Milk, volume, FillBeans) }
            FillBeans -> { volume -> fill(Resource.CoffeeBeans, volume, FillCups) }
            FillCups -> { volume -> fill(Resource.CoffeeBeans, volume, MainMenu) }
            else -> ::mainMenu
        }(request)
    }

    override fun switchTo(state: ControlState) {
        controlState = state
        display.accept(state.prompt)
    }

    private fun mainMenu(request: String) {
        when (request) {
            "buy" -> switchTo(BuyCoffee)
            "fill" -> switchTo(FillWater)
            "take" -> withdrawCash()
            "remaining" -> storageState()
            "exit" -> switchTo(Shutdown)
        }
    }

    private fun withdrawCash() {
        val cash = storage.volume(Resource.Cash)
        storage.take(Resource.Cash, cash)
        display.accept("I gave you \$$cash")
    }

    private fun storageState() {
        val report = Resource.values()
            .joinToString("\n", "The coffee machine has:\n")
            { it.status(storage.volume(it)) }
        display.accept(report)
    }

    private fun fill(resource: Resource, volume: String, nextState: ControlState) {
        storage.fill(resource, volume.toInt())
        switchTo(nextState)
    }
}