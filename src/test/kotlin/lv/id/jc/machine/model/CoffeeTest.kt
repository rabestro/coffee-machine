package lv.id.jc.machine.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CoffeeTest {

    @ParameterizedTest(name = "should have the correct recipe for {0}")
    @MethodSource("provideRecipes")
    @DisplayName("should have the correct recipe for coffee")
    fun getRecipe(coffee: Coffee, expectedRecipe: Map<Resource, Int>) {
        Assertions.assertEquals(expectedRecipe, coffee.recipe, "wrong recipe for $coffee")
    }

    private fun provideRecipes(): Stream<Arguments> {
        return Stream.of(
            Arguments.of(
                Coffee.Espresso, mapOf(Resource.Water to 250, Resource.CoffeeBeans to 16)
            ),
            Arguments.of(
                Coffee.Latte, mapOf(Resource.Water to 350, Resource.Milk to 74, Resource . CoffeeBeans to 20)
            ),
            Arguments.of(
                Coffee.Cappuccino, mapOf(Resource.Water to 200, Resource.Milk to 100, Resource . CoffeeBeans to 12)
            )
        )
    }

    @ParameterizedTest(name = "should have a fair price for {0}")
    @DisplayName("should have a fair price for coffee")
    @CsvSource(value = ["Espresso, 4", "Latte, 7", "Cappuccino, 6"])
    fun getPrice(coffee: Coffee, fairPrice: Int) {
        Assertions.assertEquals(fairPrice, coffee.price)
    }
}