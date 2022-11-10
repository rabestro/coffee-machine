package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.exception.NotEnoughResourcesException
import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.StorageUnit

class StorageBlock : StorageUnit {
    private val containers = IntArray(Resource.values().size) { 0 }

    override fun volume(resource: Resource) = containers[resource.ordinal]

    override fun allocateResources(beverage: Coffee) {
        checkResources(beverage)

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

    private fun checkResources(beverage: Coffee) {
        val missingResources = missingResources(beverage)
        if (missingResources.isNotEmpty()) {
            throw NotEnoughResourcesException(missingResources.toSet())
        }
    }

    private fun requiredResources(beverage: Coffee) = beverage.recipe + Pair(Resource.DisposableCups, 1)

    private fun missingResources(beverage: Coffee) = requiredResources(beverage)
        .filter { notEnough(it.key, it.value) }
        .map { it.key }

    private fun notEnough(resource: Resource, volume: Int) = containers[resource.ordinal] < volume
}