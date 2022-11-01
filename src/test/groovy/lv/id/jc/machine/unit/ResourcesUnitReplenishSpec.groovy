package lv.id.jc.machine.unit


import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('Technician replenishes the resources in the coffee machine')
@Narrative('''
As a technician
I want to replenish the necessary resources in the resource block
So that the coffee machine can prepare the necessary coffee drinks
''')
@Subject(ResourcesUnit)
class ResourcesUnitReplenishSpec extends Specification {

    def 'should replenish the specified resource in the resources unit'() {

        given: 'resource unit with a particular initial volume of resources'
        def resourcesUnit = new ResourcesUnit()

        with(resourcesUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Money, money)
        }

        when: 'we replenish a particular ingredient'
        resourcesUnit.fill(resource, replenishment)

        then: 'the volume of this resource has increased due to the added replenishment'
        resourcesUnit.volume(resource) == old(resourcesUnit.volume(resource)) + replenishment

        where: 'the initial volume of resources'
        water | milk | beans | cups | money | resource | replenishment
        0     | 0    | 0     | 0    | 0     | Water    | 5000
        400   | 540  | 20    | 9    | 550   | Milk     | 2500

    }
}
