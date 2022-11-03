package lv.id.jc.machine.unit

import lv.id.jc.machine.state.State

interface ControlUnit {
    fun process(request: String)
    fun switchTo(state: State)
}