package lv.id.jc.machine.sample.testfails


import spock.lang.Specification

import static lv.id.jc.machine.model.Coffee.Espresso
import static lv.id.jc.machine.model.Resource.CoffeeBeans
import static lv.id.jc.machine.model.Resource.Water

class CoffeeEspressoRecipeSpec extends Specification {

    def 'should be the proper recipe for coffee drinks'() {

        expect:
        beverage.recipe == properRecipe

        where:
        beverage = Espresso
        properRecipe = [(Water): 250, (CoffeeBeans): 16]
    }
}


