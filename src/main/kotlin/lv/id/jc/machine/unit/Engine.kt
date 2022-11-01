package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.state.State

class Engine(val display: (String) -> Unit = ::println) {
    var state: State = State.MainMenu

    private val containers = Resource.values().associateWith { Container(it, 0) }

    fun status() = Resource.values()
        .joinToString("\n", "The coffee machine has:\n")
        { containers[it]!!.status() }

    fun volume(resource: Resource) = containers[resource]!!.volume

    fun take(resource: Resource, volume: Int) {
        containers[resource]!!.take(volume)
    }

    fun fill(resource: Resource, volume: Int) {
        containers[resource]!!.replenish(volume)
    }
}