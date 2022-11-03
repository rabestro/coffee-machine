package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Resource

class StorageBlock() : StorageUnit {
    private val containers = Resource.values().associateWith { Container(it, 0) }

    override fun volume(resource: Resource) = containers[resource]?.volume ?: 0

    override fun take(resource: Resource, volume: Int) {
        containers[resource]!!.take(volume)
    }

    override fun fill(resource: Resource, volume: Int) {
        containers[resource]!!.replenish(volume)
    }

}