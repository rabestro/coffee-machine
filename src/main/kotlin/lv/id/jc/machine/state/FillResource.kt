package lv.id.jc.machine.state

import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.Engine

class FillResource(
    private val engine: Engine,
    private val resource: Resource,
    private val nextState: State
) : Command {

    override fun process(request: String) {
        engine.fill(resource, request.toInt())
        engine.state = nextState
    }
}