package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Resource

class Container(private val resource: Resource, var volume: Int = 0) {

    fun replenish(volume: Int) {
        this.volume += volume
    }

    fun hasVolume(volume: Int) = this.volume >= volume

    fun take(volume: Int) {
        this.volume -= volume
    }

}