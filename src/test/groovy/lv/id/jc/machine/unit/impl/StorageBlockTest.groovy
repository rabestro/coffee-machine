package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.exception.NotEnoughResourcesException
import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.tag.UnitTest
import lv.id.jc.machine.unit.UnitSpecification
import spock.lang.Subject
import spock.lang.Title

import java.util.concurrent.ThreadLocalRandom

import static lv.id.jc.machine.model.Resource.*

/**
 * Classic approach when we write one test class for one java class.
 */
@UnitTest
@Subject(StorageBlock)
@Title('Coffee machine storage unit')
class StorageBlockTest extends UnitSpecification {

    def 'should be completely empty after creation'() {

        given: 'newly created storage block'
        def storage = new StorageBlock()

        expect: 'the initial value of all resources are zero'
        Resource.values()
                .collect { storage.volume(it) }
                .every { it == 0 }
    }

    def 'should replenish the resource'(Resource resource) {

        given: 'newly created storage block'
        def storage = new StorageBlock()

        expect: 'the initial value of the resource volume is zero'
        storage.volume(resource) == 0

        when: 'we replenish this resource'
        storage.fill(resource, replenishment)

        then: 'the volume of this resource becomes equal to the replenishment'
        storage.volume(resource) == replenishment

        where: 'resource for replenishment and volume'
        resource << Resource.values()
        replenishment << Resource.values().collect { someAmount() }
    }

    def 'should throw an exception if not enough resources for coffee'() {

        given: 'storage unit with insufficient resources'
        def storage = storageOf water, milk, beans, cups

        when: 'we are trying to allocate resources for the selected coffee drink'
        storage.allocateResources beverage

        then: 'throws insufficient resources exception'
        def exception = thrown NotEnoughResourcesException

        and: 'the exception contains a list of all missing resources'
        exception.missingResources ==~ missingResources

        where: 'resources in storage device, coffee drink and missing ingredients'
        water | milk | beans | cups | beverage          | missingResources
        0     | 0    | 0     | 0    | Coffee.Espresso   | [Water, CoffeeBeans, DisposableCups]
        249   | 300  | 15    | 0    | Coffee.Espresso   | [Water, CoffeeBeans, DisposableCups]
        349   | 74   | 19    | 0    | Coffee.Latte      | [Water, Milk, CoffeeBeans, DisposableCups]
        0     | 0    | 0     | 0    | Coffee.Latte      | [Water, Milk, CoffeeBeans, DisposableCups]
        0     | 0    | 0     | 1    | Coffee.Latte      | [Water, Milk, CoffeeBeans]
        0     | 0    | 20    | 1    | Coffee.Latte      | [Water, Milk]
        0     | 75   | 20    | 1    | Coffee.Latte      | [Water]
        199   | 99   | 11    | 0    | Coffee.Cappuccino | [Water, Milk, CoffeeBeans, DisposableCups]
        455   | 395  | 84    | 0    | Coffee.Cappuccino | [DisposableCups]
        800   | 650  | 0     | 45   | Coffee.Cappuccino | [CoffeeBeans]
        750   | 0    | 95    | 7    | Coffee.Cappuccino | [Milk]
        0     | 300  | 45    | 9    | Coffee.Cappuccino | [Water]
    }

    def 'should collect money and allocate resources if enough resources for coffee'() {

        given: 'a storage device with enough resources'
        def storage = storageOf water, milk, beans, cups, cash

        when: 'we are trying to allocate resources for the selected coffee drink'
        storage.allocateResources beverage

        then: 'no exception is thrown when there are enough resources'
        notThrown NotEnoughResourcesException

        and: 'cash received according to the price of coffee'
        storage.volume(Cash) == old(storage.volume(Cash)) + beverage.price

        and: 'one cup for a coffee drink is allocated'
        storage.volume(DisposableCups) == cups - 1

        and: 'the necessary ingredients for the drink are dispensed'
        storage.volume(Water) == water - beverage.volume(Water)
        storage.volume(Milk) == milk - beverage.volume(Milk)
        storage.volume(CoffeeBeans) == beans - beverage.volume(CoffeeBeans)

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

        and: 'cash collected for previously sold coffee'
        cash = someAmount()
    }

    def 'should withdraw collected cash'() {

        given: 'a storage device with some resources'
        def storage = storageOf water, milk, beans, cups, cash

        expect: 'the storage has collected money for sold coffee drinks'
        storage.volume(Cash) == cash

        when: 'we withdraw cash'
        storage.withdrawCash()

        then: 'the storage has no more cash'
        storage.volume(Cash) == 0

        and: 'the amount of all other resources remained unchanged'
        with(storage) {
            volume(Water) == water
            volume(Milk) == milk
            volume(CoffeeBeans) == beans
            volume(DisposableCups) == cups
        }

        where: 'amount of resources in the storage'
        water | milk | beans | cups | cash
        250   | 0    | 16    | 1    | 0
        3750  | 2400 | 98    | 17   | 800
        600   | 400  | 90    | 0    | 950
    }

    def someAmount() {
        ThreadLocalRandom.current().nextInt(1000)
    }
}
