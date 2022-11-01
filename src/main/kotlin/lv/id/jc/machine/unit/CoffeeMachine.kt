package lv.id.jc.machine.unit

import lv.id.jc.machine.state.State

class CoffeeMachine(private var state: State) {
    private var output = ""

    fun prompt(): String = state.prompt

    fun process(request: String) {
        state.process(request)
        output = state.output
        state = state.next()
    }

    fun output() = output

    fun isOperate() = state != State.Exit
}
