package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('The storage device allocates resources to prepare a coffee drink')
@Narrative('''
As a control unit
I want to receive the necessary resources from the storage unit
So that I will be able to prepare the desired coffee drink
''')
class StorageUnitAllocateResourcesSpec extends Specification {

    def 'should allocate the necessary resources for making coffee'() {

        given: 'a storage unit with enough of all the necessary resources'
        @Subject def storageUnit = new StorageBlock()
        with(storageUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
        }

        when: 'we are requesting resource allocation for a coffee drink'
        storageUnit.allocateResources(beverage)

        then: 'the request executes normally without any exceptions.'
        noExceptionThrown()

        and: 'the amount of resources in the storage unit is reduced accordingly'
        with(storageUnit) {
            volume(Water) == water - beverage.recipe.get(Water, 0)
            volume(Milk) == milk - beverage.recipe.get(Milk, 0)
            volume(CoffeeBeans) == beans - beverage.recipe.get(CoffeeBeans, 0)
            volume(DisposableCups) == cups - 1
        }

        where: 'resources in storage device and desired coffee drink'
        water | milk | beans | cups | beverage
        250   | 0    | 16    | 1    | Coffee.Espresso
        3750  | 2400 | 98    | 17   | Coffee.Espresso
        5000  | 4000 | 90    | 55   | Coffee.Espresso
        3000  | 2500 | 20    | 9    | Coffee.Latte
        355   | 75   | 20    | 5    | Coffee.Latte
        350   | 75   | 20    | 1    | Coffee.Latte
        360   | 76   | 21    | 2    | Coffee.Latte
        200   | 100  | 12    | 1    | Coffee.Cappuccino
        8000  | 6800 | 450   | 120  | Coffee.Cappuccino
    }
}
