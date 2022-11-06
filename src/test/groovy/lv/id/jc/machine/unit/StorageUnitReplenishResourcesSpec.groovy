package lv.id.jc.machine.unit

import spock.lang.Narrative
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.Milk
import static lv.id.jc.machine.model.Resource.Water

@Title('The control unit replenishes the resources in the storage unit')
@Narrative('''
As a control unit
I want to replenish the necessary resources in the coffee machine
So that the coffee machine can prepare the necessary coffee drinks
''')
class StorageUnitReplenishResourcesSpec extends UnitSpecification {

    def 'should replenish the specified resource'() {

        given: 'storage unit with a particular initial volume of resources'
        @Subject def storage = storageOf water, milk, beans, cups, cash

        when: 'we replenish a particular resource'
        storage.fill target, replenishment

        then: 'the volume of this resource has increased due to the added replenishment'
        storage.volume(target) == old(storage.volume(target)) + replenishment

        where: 'the initial volume of resources'
        water | milk | beans | cups | cash | target | replenishment
        0     | 0    | 0     | 0    | 0    | Water  | 5000
        400   | 540  | 20    | 9    | 550  | Milk   | 2500
    }
}
