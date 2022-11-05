package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Coffee
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('The control device queries for the missing coffee drink ingredients')
@Narrative('''
As control unit
I want to know if there are enough ingredients to make a coffee drink
So that I will know if it is possible to prepare a drink
''')
class StorageUnitMissingResourcesSpec extends Specification {

    def 'should return the missing resources for the required drink'() {

        given: 'a storage unit with a certain amount of resources'

        and: 'a control unit with a fake display'

        when: 'we are querying the storage unit about the missing ingredients for making a coffee drink'

        then: 'we get a set containing the missing ingredients'

        where: 'resources in storage device, coffee drink and missing ingredients'
        water | milk | beans | cups | drink             | required
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
