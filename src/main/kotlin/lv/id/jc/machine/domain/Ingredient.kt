package lv.id.jc.machine.domain

enum class Ingredient(val description: String, val unit: String) {
    Water("water", "ml"),
    Milk("milk", "ml"),
    CoffeeBeans("coffee beans", "g");
}