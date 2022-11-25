package lv.id.jc.machine.sample.datatables

import lv.id.jc.machine.exception.NotEnoughResourcesException
import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Specification
import spock.lang.Unroll

import static lv.id.jc.machine.model.Resource.*

class StorageUnitAllocateResourcesSpec extends Specification {

    @Unroll('for #beverage required resources are #requiredResources')
    def 'allocate resources for beverage when the storage is empty'() {
        given:
        def storageUnit = new StorageBlock()

        when:
        storageUnit.allocateResources beverage

        then:
        def exception = thrown NotEnoughResourcesException

        and:
        exception.message == 'not enough resources'
        exception.missingResources ==~ requiredResources

        where:
        beverage          | requiredResources
        Coffee.Espresso   | [Water, CoffeeBeans, DisposableCups]
        Coffee.Latte      | [Water, Milk, CoffeeBeans, DisposableCups]
        Coffee.Cappuccino | [Water, Milk, CoffeeBeans, DisposableCups]
    }
}

