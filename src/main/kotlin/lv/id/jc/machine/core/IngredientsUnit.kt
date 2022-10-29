package lv.id.jc.machine.core

import lv.id.jc.machine.domain.Ingredient

class IngredientsUnit(ingredients: Map<Ingredient, Int> = emptyMap()) {
    private val containers = mutableMapOf<Ingredient, Int>()

    init {
        containers.putAll(ingredients)
    }

    fun fill(ingredient: Ingredient, volume: Int) {
        containers[ingredient] = containers.getOrDefault(ingredient, 0) + volume
    }

    fun status(): Map<Ingredient, Int> {
        return Ingredient.values().associateWith { containers.getOrDefault(it, 0) }
    }
}