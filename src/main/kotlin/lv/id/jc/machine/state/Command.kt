package lv.id.jc.machine.state

sealed interface Command {
    fun process(request: String)
}