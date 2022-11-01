package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Resource

class Container(private val type: Resource, var volume: Int = 0) {

    fun replenish(replenishment: Int) {
        volume += replenishment
    }

    fun hasResource(minimum: Int) = volume >= minimum

    fun status() = type.status(volume)

    fun take(resource: Int) {
        volume -= resource
    }

}