package lv.id.jc.machine.unit

import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('Technician replenishes the resources in the coffee machine')
@Narrative('''
As a technician
I want to replenish the necessary resources in the coffee machine
So that the coffee machine can prepare the necessary coffee drinks
''')
class StoreUnitReplenishSpec extends Specification {

    def 'should replenish the specified resource in the coffee machine'() {

        given: 'resource unit with a particular initial volume of resources'
        @Subject def storage = new StorageBlock()

        with(storage) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Cash, money)
        }

        when: 'we replenish a particular resource'
        storage.fill(target, replenishment)

        then: 'the volume of this resource has increased due to the added replenishment'
        storage.volume(target) == old(storage.volume(target)) + replenishment

        where: 'the initial volume of resources'
        water | milk | beans | cups | money | target | replenishment
        0     | 0    | 0     | 0    | 0     | Water  | 5000
        400   | 540  | 20    | 9    | 550   | Milk   | 2500

    }
}
