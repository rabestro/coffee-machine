package lv.id.jc.machine.core

import lv.id.jc.machine.domain.Ingredient

class IngredientsUnit(ingredients: Map<Ingredient, Int> = emptyMap()) : Unit {
    private val containers = mutableMapOf<Ingredient, Int>()

    init {
        containers.putAll(ingredients)
    }

    constructor(water: Int, milk: Int, beans: Int) : this(
        mutableMapOf(
            Ingredient.Water to water, Ingredient.Milk to milk, Ingredient.CoffeeBeans to beans
        )
    )

    fun fill(ingredient: Ingredient, volume: Int) {
        containers[ingredient] = containers.getOrDefault(ingredient, 0) + volume
    }

    override fun status() = Ingredient.values()
        .joinToString("\n")
        { "${containers.getOrDefault(it, 0)} ${it.unit} of ${it.description}" }

}