package lv.id.jc.machine.model

import lv.id.jc.machine.model.Resource.*

enum class Coffee(val recipe: Map<Resource, Int>, val price: Int) {
    Espresso(mapOf(Water to 250, CoffeeBeans to 16), 4),
    Latte(mapOf(Water to 350, Milk to 75, CoffeeBeans to 20), 7),
    Cappuccino(mapOf(Water to 200, Milk to 100, CoffeeBeans to 12), 6);

    fun number() = (ordinal + 1).toString()

    fun volume(ingredient: Resource) = recipe.getOrDefault(ingredient, 0)
}