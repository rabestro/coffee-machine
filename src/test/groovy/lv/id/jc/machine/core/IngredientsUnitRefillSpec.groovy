package lv.id.jc.machine.core

import lv.id.jc.machine.domain.Ingredient
import org.junit.Ignore
import spock.lang.Narrative
import spock.lang.PendingFeature
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.domain.Ingredient.*

@Title('Technician refills ingredients in coffee machine')
@Narrative('''
As a technician
I want to replenish the necessary ingredients in the ingredient block
So that the coffee machine can prepare the necessary coffee drinks
''')
@Subject(IngredientsUnit)
class IngredientsUnitRefillSpec extends Specification {

    @PendingFeature
    def 'should replenish the specified ingredient in the ingredients unit'() {

        given: 'an ingredient block with a particular initial amount of ingredients'
        def unit = new IngredientsUnit(initial as Map<Ingredient, Integer>)

        when: 'we replenish a particular ingredient'
        unit.fill(ingredient, volume)

        then: 'the total volume of this ingredient increased by the amount added'
        unit.status() == expected

        where:
        initial                     | ingredient  | volume || expected
        []                          | Water       | 500    || [(Water): 500, (Milk): 0, (CoffeeBeans): 0]
        []                          | Milk        | 300    || [(Water): 0, (Milk): 300, (CoffeeBeans): 0]
        []                          | CoffeeBeans | 125    || [(Water): 0, (Milk): 0, (CoffeeBeans): 125]
        [(Water): 1000]             | CoffeeBeans | 275    || [(Water): 1000, (Milk): 0, (CoffeeBeans): 275]
        [(Water): 1000]             | Water       | 2500   || [(Water): 3500, (Milk): 0, (CoffeeBeans): 0]
        [(Water): 100, (Milk): 300] | Water       | 500    || [(Water): 600, (Milk): 300, (CoffeeBeans): 0]
    }
}
