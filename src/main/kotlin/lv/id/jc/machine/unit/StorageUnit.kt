package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Resource

interface StorageUnit {
    fun fill(resource: Resource, volume: Int)
    fun take(resource: Resource, volume: Int)
    fun volume(resource: Resource): Int
}