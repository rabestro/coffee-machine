package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.StorageUnit

class StorageBlock : StorageUnit {
    private val containers = IntArray(Resource.values().size) { 0 }

    override fun volume(resource: Resource) = containers[resource.ordinal]

    override fun allocateResources(beverage: Coffee) {
        val missingResources = missingResources(beverage)
        require(missingResources.isEmpty()) { "Sorry, not enough resources: $missingResources!" }

        beverage.recipe.forEach {
            containers[it.key.ordinal] -= it.value
        }
        containers[Resource.DisposableCups.ordinal]--
        containers[Resource.Cash.ordinal] += beverage.price
    }

    override fun withdrawCash() {
        containers[Resource.Cash.ordinal] = 0
    }

    override fun fill(resource: Resource, volume: Int) {
        containers[resource.ordinal] += volume
    }

    private fun missingResources(beverage: Coffee): List<Resource> {
        val requiredResources = beverage.recipe + Pair(Resource.DisposableCups, 1)
        return requiredResources
            .filter { notEnough(it.key, it.value) }
            .map { it.key }
    }

    private fun notEnough(resource: Resource, volume: Int) = containers[resource.ordinal] < volume
}