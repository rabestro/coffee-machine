package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Coffee
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('The storage device allocates resources to prepare a coffee drink')
@Narrative('''
As a control unit
I want to receive the necessary resources from the storage unit
So that I will be able to prepare the desired coffee drink
''')
class StorageUnitAllocateResourcesSpec extends Specification {

    def 'should allocate the necessary resources for making coffee'() {

        given: 'a storage unit with enough of all the necessary resources'

        when: 'we are requesting resource allocation for a coffee drink'

        then: 'the request executes normally without any exceptions.'

        and: 'the amount of resources in the storage unit is reduced accordingly'

        where: 'resources in storage device and desired coffee drink'
        water | milk | beans | cups | drink
        5000  | 4000 | 90    | 55   | Coffee.Espresso
        3000  | 2500 | 20    | 9    | Coffee.Latte
        355   | 75   | 20    | 5    | Coffee.Latte
        350   | 75   | 20    | 1    | Coffee.Latte
        360   | 76   | 21    | 2    | Coffee.Latte
        200   | 100  | 12    | 1    | Coffee.Cappuccino
    }
}
