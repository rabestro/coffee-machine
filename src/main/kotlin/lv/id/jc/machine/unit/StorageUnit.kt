package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Resource

interface StorageUnit {
    fun fill(resource: Resource, volume: Int)
    fun volume(resource: Resource): Int
    fun allocateResources(beverage: Coffee)
    fun withdrawCash()
}