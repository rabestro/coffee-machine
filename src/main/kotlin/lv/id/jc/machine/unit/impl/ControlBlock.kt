package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.model.ControlState.*
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.ControlUnit
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.StorageUnit
import java.lang.IllegalArgumentException

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
            FillCups -> { volume -> fill(Resource.DisposableCups, volume, MainMenu) }
            BuyCoffee -> ::buyCoffee
            Shutdown -> { _ -> }
        }(request)
    }

    override fun switchTo(state: ControlState) {
        controlState = state
        display.accept(state.prompt)
    }

    override fun isOperate(): Boolean = controlState != Shutdown

    private fun mainMenu(request: String) {
        when (Command.valueOf(request.uppercase())) {
            Command.BUY -> switchTo(BuyCoffee)
            Command.FILL -> switchTo(FillWater)
            Command.TAKE -> withdrawCash()
            Command.REMAINING -> storageState()
            Command.EXIT -> switchTo(Shutdown)
            else -> {}
        }
    }

    private fun buyCoffee(request: String) {
        when (request.uppercase()) {
            Coffee.Espresso.number() -> make(Coffee.Espresso)
            Coffee.Latte.number() -> make(Coffee.Latte)
            Coffee.Cappuccino.number() -> make(Coffee.Cappuccino)
        }
        switchTo(MainMenu)
    }

    private fun make(beverage: Coffee) {
        val message = try {
            storage.allocateResources(beverage)
            "I have enough resources, making you a coffee!"
        } catch (exception: IllegalArgumentException) {
            exception.message ?: "Sorry, not enough resources!"
        }
        display.accept(message)
    }

    private fun withdrawCash() {
        val cash = storage.volume(Resource.Cash)
        storage.withdrawCash()
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