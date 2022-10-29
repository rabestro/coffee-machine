package lv.id.jc.machine.core

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

import static lv.id.jc.machine.domain.Ingredient.*

@Title('Technician checks remaining ingredients in coffee machine')
@Narrative('''
As a technician
I want to checks remaining ingredients in coffee machine
So that I know which ingredients need to be refilled
''')
class IngredientsUnitStatusSpec extends Specification {

    def 'should return the volume of the remaining ingredients'() {

        given: 'an ingredient block with a particular amount of ingredients'

        when: 'we request a report on the remaining amount of ingredients'

        then: 'we get a list of all ingredients with the remaining volume'

        where:
        initial | expected
        []      | [(Water): 0, (Milk): 0, (CoffeeBeans): 0]
    }
}
