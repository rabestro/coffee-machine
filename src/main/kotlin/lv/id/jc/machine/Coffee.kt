package lv.id.jc.machine

import lv.id.jc.machine.Ingredient.*

enum class Coffee(val recipe: Map<Ingredient, Int>, val price: Int) {
    Espresso(mapOf(Water to 250, CoffeeBeans to 16), 4),
    Latte(mapOf(Water to 350, Milk to 75, CoffeeBeans to 20), 7),
    Cappuccino(mapOf(Water to 200, Milk to 100, CoffeeBeans to 12), 6)
}