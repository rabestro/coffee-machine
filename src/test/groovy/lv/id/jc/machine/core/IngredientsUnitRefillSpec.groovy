package lv.id.jc.machine.core

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

import static lv.id.jc.machine.domain.Ingredient.*

@Title('Technician refills ingredients in coffee machine')
@Narrative('''
As a technician
I want to replenish the necessary ingredients in the ingredient block
So that the coffee machine can prepare the necessary coffee drinks
''')
class IngredientsUnitRefillSpec extends Specification {

    def 'should replenish the specified ingredient in the ingredients unit'() {

        given: 'an ingredient block with a particular initial amount of ingredients'

        when: 'we replenish a particular ingredient'

        then: 'the total volume of this ingredient increased by the amount added'

        and: 'the volume of other ingredients remained the same'

        where:
        initial | ingredient  | replenishment | expected
        []      | Water       | 500           | [(Water): 500]
        []      | Milk        | 300           | [(Milk): 300]
        []      | CoffeeBeans | 125           | [(CoffeeBeans): 125]
    }
}
