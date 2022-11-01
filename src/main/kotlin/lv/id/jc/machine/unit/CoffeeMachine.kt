package lv.id.jc.machine.unit

import lv.id.jc.machine.state.Command
import lv.id.jc.machine.state.State

class CoffeeMachine(
    private val engine: Engine,
    private val commands: Map<State, Command>) {

    fun prompt() = engine.display(engine.state.prompt)

    fun process(request: String) {
        commands[engine.state]?.process(request)
    }

    fun isOperate() = engine.state != State.Exit
}
