package lv.id.jc.machine.state

enum class State(val prompt:String) {
    MainMenu("Write action (buy, fill, take, remaining, exit): "),
    BuyCoffee("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:"),
    FillWater("Write how many ml of water you want to add:"),
    FillMilk("Write how many ml of milk you want to add:"),
    FillBeans("Write how many grams of coffee beans you want to add:"),
    FillCups("Write how many disposable cups you want to add:"),
    Exit("")
}