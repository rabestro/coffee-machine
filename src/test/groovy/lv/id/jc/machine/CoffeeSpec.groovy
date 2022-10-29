package lv.id.jc.machine

import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.Ingredient.*

@Title('Coffee drinks recipe and price')
@Narrative('''
As a coffee lover
I want the coffee to be made to the right recipe and at a fair price
So that I will be able to enjoy the beverage
''')
@Subject(Coffee)
@See('https://hyperskill.org/projects/67/stages/364/implement')
class CoffeeSpec extends Specification {

    def 'should have the correct recipe and price for #coffee'() {

        expect: 'coffee drink prepared according to the correct recipe'
        coffee.recipe == recipe

        and: 'a coffee drink is offered at an appropriate price'
        coffee.price == price

        where: 'the coffee drinks with correct recipe and fair price'
        coffee            | price | recipe
        Coffee.Espresso   | 4     | [(Water): 250, (CoffeeBeans): 16]
        Coffee.Latte      | 7     | [(Water): 350, (Milk): 75, (CoffeeBeans): 20]
        Coffee.Cappuccino | 6     | [(Water): 200, (Milk): 100, (CoffeeBeans): 12]
    }

}
