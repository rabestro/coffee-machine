package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.StorageUnit
import java.lang.IllegalArgumentException

class StorageBlock : StorageUnit {
    private val containers = IntArray(Resource.values().size) { 0 }

    override fun volume(resource: Resource) = containers[resource.ordinal]

    override fun missingResources(coffee: Coffee): Set<Resource> {
        val requiredResources = coffee.recipe + Pair(Resource.DisposableCups, 1)

        return requiredResources
            .filter { notEnough(it.key, it.value) }
            .map { it.key }.toSet()
    }

    override fun take(resource: Resource, volume: Int) {
        if (notEnough(resource, volume)) {
            throw IllegalArgumentException("Sorry, not enough ${resource.resourceName}!")
        }
        containers[resource.ordinal] -= volume
    }

    override fun fill(resource: Resource, volume: Int) {
        containers[resource.ordinal] += volume
    }

    private fun notEnough(resource: Resource, volume: Int) = containers[resource.ordinal] < volume
}