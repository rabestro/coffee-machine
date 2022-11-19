package lv.id.jc.machine.model


import spock.lang.*

import static lv.id.jc.machine.model.Resource.*

@Title('Coffee drinks recipe and price')
@Narrative('''
As a coffee lover
I want the coffee drink to meet specifications
So that I will be able to enjoy the beverage
''')
@Subject(Coffee)
@See('https://hyperskill.org/projects/67/stages/364/implement')
@Tag('Sanity')
class CoffeeSpec extends Specification {

    def 'should have the correct recipe for #coffee'() {

        expect: 'coffee recipe meets specification'
        coffee.recipe == expectedRecipe

        where: 'the coffee drinks and correct recipe'
        coffee            | expectedRecipe
        Coffee.Espresso   | [(Water): 250, (CoffeeBeans): 16]
        Coffee.Latte      | [(Water): 350, (Milk): 75, (CoffeeBeans): 20]
        Coffee.Cappuccino | [(Water): 200, (Milk): 100, (CoffeeBeans): 12]
    }

    def 'should have a fair price for #coffee'() {

        expect: 'the beverage has a fair price'
        coffee.price == fairPrice

        where: 'the coffee drinks with fair price'
        coffee            | fairPrice
        Coffee.Espresso   | 4
        Coffee.Latte      | 7
        Coffee.Cappuccino | 6
    }
}
