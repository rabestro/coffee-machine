package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.*

import static lv.id.jc.machine.model.Resource.*

@Title('The storage device reports missing resources')
@Narrative('''
As a control unit
I want to know if there are enough resources in a storage unit to make a coffee drink
So that I will know if it is possible to prepare a beverage
''')
@Issue('17')
@See('https://github.com/rabestro/coffee-machine/wiki/Buy-coffee-drink')
class StorageUnitMissingResourcesSpec extends Specification {

    def 'should return the missing resources for the required drink'() {

        given: 'a storage unit with a certain amount of resources'
        @Subject def storageUnit = new StorageBlock()

        with(storageUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
        }

        when: 'we are querying the storage unit about the missing ingredients for making a coffee drink'
        def actual = storageUnit.missingResources(drink)

        then: 'we get a set containing the missing ingredients'
        actual == missingResources as Set

        where: 'resources in storage device, coffee drink and missing ingredients'
        water | milk | beans | cups | drink             | missingResources
        0     | 0    | 0     | 0    | Coffee.Espresso   | [Water, CoffeeBeans, DisposableCups]
        0     | 0    | 0     | 0    | Coffee.Latte      | [Water, Milk, CoffeeBeans, DisposableCups]
        0     | 0    | 0     | 1    | Coffee.Latte      | [Water, Milk, CoffeeBeans]
        0     | 0    | 20    | 1    | Coffee.Latte      | [Water, Milk]
        0     | 75   | 20    | 1    | Coffee.Latte      | [Water]
        350   | 75   | 20    | 1    | Coffee.Latte      | []
        349   | 74   | 19    | 0    | Coffee.Latte      | [Water, Milk, CoffeeBeans, DisposableCups]
        360   | 76   | 21    | 2    | Coffee.Latte      | []
        200   | 100  | 12    | 1    | Coffee.Cappuccino | []
        199   | 99   | 11    | 0    | Coffee.Cappuccino | [Water, Milk, CoffeeBeans, DisposableCups]
        455   | 395  | 84    | 0    | Coffee.Cappuccino | [DisposableCups]
        800   | 650  | 0     | 45   | Coffee.Cappuccino | [CoffeeBeans]
        750   | 0    | 95    | 7    | Coffee.Cappuccino | [Milk]
        0     | 300  | 45    | 9    | Coffee.Cappuccino | [Water]
    }
}
