package lv.id.jc.machine.unit

import lv.id.jc.machine.model.ControlState

interface ControlUnit {
    fun process(request: String)
    fun switchTo(state: ControlState)
    fun isOperate(): Boolean
}