package lv.id.jc.machine.state

sealed class State(val prompt: String, var output: String = "") {
    abstract fun process(request: String)
    abstract fun next(): State

    object Exit : State("") {
        override fun process(request: String) {}
        override fun next() = this
    }
}
