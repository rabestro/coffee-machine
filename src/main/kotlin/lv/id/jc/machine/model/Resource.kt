package lv.id.jc.machine.model

enum class Resource(val resourceName: String, val status: (Int) -> String) {
    Water("water", { "$it ml of water" }),
    Milk("milk", { "$it ml of milk" }),
    CoffeeBeans("coffee beans", { "$it g of coffee beans" }),
    DisposableCups("disposable cups", { "$it disposable cups" }),
    Cash("money", { "$$it of money" })
}
