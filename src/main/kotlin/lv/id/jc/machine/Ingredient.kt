package lv.id.jc.machine

enum class Ingredient(val description: String, val units: String) {
    Water("water", "ml"),
    Milk("milk", "ml"),
    CoffeeBeans("coffee beans", "gr");
}