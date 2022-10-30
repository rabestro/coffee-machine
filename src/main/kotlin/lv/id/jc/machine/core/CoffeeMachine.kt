package lv.id.jc.machine.core

class CoffeeMachine(
    private val ingredientsUnit: IngredientsUnit,
    private var disposableCupsUnit: DisposableCupsUnit,
    private var moneyUnit: MoneyUnit
) : Unit {

    override fun status() = listOf(ingredientsUnit, disposableCupsUnit, moneyUnit).joinToString(
        "\n",
        "The coffee machine has:\n"
    ) { it.status() }
}