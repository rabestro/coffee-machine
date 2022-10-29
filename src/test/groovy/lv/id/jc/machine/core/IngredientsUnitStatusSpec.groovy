package lv.id.jc.machine.core

import lv.id.jc.machine.domain.Ingredient
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.domain.Ingredient.*

@Title('Technician checks remaining ingredients in coffee machine')
@Narrative('''
As a technician
I want to checks remaining ingredients in coffee machine
So that I know which ingredients need to be refilled
''')
@Subject(IngredientsUnit)
class IngredientsUnitStatusSpec extends Specification {

    def 'should return the volume of the remaining ingredients'() {

        given: 'an ingredient unit with a particular amount of ingredients'
        def unit = new IngredientsUnit(initial as Map<Ingredient, Integer>)

        when: 'we request a report on the remaining amount of ingredients'
        def actual = unit.status()

        then: 'we get a list of all ingredients with the remaining volume'
        actual == expected

        where: 'initial volume of ingredients and expected status report'
        initial                                      | water | milk | beans
        []                                           | 0     | 0    | 0
        [(Water): 1000]                              | 1000  | 0    | 0
        [(Milk): 500]                                | 0     | 500  | 0
        [(CoffeeBeans): 275]                         | 0     | 0    | 275
        [(Water): 2500, (CoffeeBeans): 385]          | 2500  | 0    | 385
        [(Water): 75, (Milk): 80, (CoffeeBeans): 95] | 75    | 80   | 95

        expected = """
$water ml of water
$milk ml of milk
$beans g of coffee beans
""".strip()

    }
}
