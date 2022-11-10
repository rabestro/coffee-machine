package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('The customer buys a coffee drink')
@Narrative('''
As a coffee lover
I want to choose and buy a coffee drink 
So that I can enjoy my favorite beverage
''')
@See('https://github.com/rabestro/coffee-machine/wiki/Buy-coffee-drink')
class ControlUnitBuySpec extends Specification {

    def 'should prepare coffee drink if enough resources'() {

        given: 'a storage unit with a certain amount of resources'
        @Subject def storageUnit = new StorageBlock()

        with(storageUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Cash, cash)
        }

        and: 'a control unit manging the storage and having a mock display'
        def displayUnit = Mock DisplayUnit
        @Subject def controlUnit = new ControlBlock(displayUnit, storageUnit)

        and: 'we switch to main menu mode'
        controlUnit.powerOn()

        when: 'the customer chooses the menu to buy coffee'
        controlUnit.process Command.BUY.name()

        then: 'the customer receives a coffee drink selection menu'
        1 * displayUnit.accept(ControlState.BuyCoffee.prompt)

        when: 'the customer chooses his favorite drink'
        controlUnit.process beverage.number()

        then: 'the display shows a message confirming that coffee is being prepared'
        1 * displayUnit.accept(message)

        and: 'the amount of resources changes in accordance with the coffee recipe and its price'
        with(storageUnit) {
            volume(DisposableCups) == cups - 1
            volume(Cash) == cash + beverage.price
            volume(Water) == expectedWater
            volume(Milk) == expectedMilk
            volume(CoffeeBeans) == expectedBeans
        }

        and: 'after making coffee, the system returns to the main menu mode'
        1 * displayUnit.accept(ControlState.MainMenu.prompt)

        where: 'initial resources in the storage unit and selected coffee drink'
        water | milk | beans | cups | cash | beverage
        250   | 0    | 16    | 1    | 280  | Coffee.Espresso
        9045  | 3080 | 2756  | 100  | 175  | Coffee.Espresso
        350   | 75   | 20    | 1    | 0    | Coffee.Latte
        360   | 76   | 21    | 2    | 928  | Coffee.Latte
        200   | 100  | 12    | 1    | 12   | Coffee.Cappuccino

        and: 'message confirming the successful preparation of a coffee drink'
        message = "I have enough resources, making you a coffee!"

        and: 'new volume of ingredients after making a coffee drink'
        expectedMilk = milk - (beverage.recipe[Milk] ?: 0)
        expectedWater = water - (beverage.recipe[Water] ?: 0)
        expectedBeans = beans - (beverage.recipe[CoffeeBeans] ?: 0)
    }

    def 'should state what is missing if not enough resources'() {

        given: 'a storage unit with a certain amount of resources'
        @Subject def storageUnit = new StorageBlock()

        with(storageUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Cash, cash)
        }

        and: 'a control unit manging the storage and having a mock display'
        def displayUnit = Mock DisplayUnit
        @Subject def controlUnit = new ControlBlock(displayUnit, storageUnit)

        and: 'we switch to main menu mode'
        controlUnit.powerOn()

        when: 'the customer chooses the menu to buy coffee'
        controlUnit.process Command.BUY.name()

        then: 'the customer receives a coffee drink selection menu'
        1 * displayUnit.accept(ControlState.BuyCoffee.prompt)

        when: 'the customer chooses his favorite drink'
        controlUnit.process beverage.number()

        then: 'a message about the lack of resources is displayed on the screen'
        1 * displayUnit.accept(message)

        and: 'the amount of available resources in the storage block does not change'
        with(storageUnit) {
            volume(DisposableCups) == cups
            volume(Cash) == cash
            volume(Water) == water
            volume(Milk) == milk
            volume(CoffeeBeans) == beans
        }

        and: 'the system returns to the main menu mode'
        1 * displayUnit.accept(ControlState.MainMenu.prompt)

        where: 'resources in storage device, coffee drink and required resources'
        water | milk | beans | cups | cash | beverage          | requiredResources
        0     | 0    | 0     | 0    | 0    | Coffee.Espresso   | [Water, CoffeeBeans, DisposableCups]
        0     | 0    | 0     | 0    | 1000 | Coffee.Latte      | [Water, Milk, CoffeeBeans, DisposableCups]
        0     | 0    | 0     | 1    | 550  | Coffee.Latte      | [Water, Milk, CoffeeBeans]
        0     | 0    | 20    | 1    | 0    | Coffee.Latte      | [Water, Milk]
        0     | 75   | 20    | 1    | 280  | Coffee.Latte      | [Water]
        349   | 74   | 19    | 0    | 950  | Coffee.Latte      | [Water, Milk, CoffeeBeans, DisposableCups]
        199   | 99   | 11    | 0    | 0    | Coffee.Cappuccino | [Water, Milk, CoffeeBeans, DisposableCups]
        455   | 395  | 84    | 0    | 15   | Coffee.Cappuccino | [DisposableCups]
        800   | 650  | 0     | 45   | 75   | Coffee.Cappuccino | [CoffeeBeans]
        750   | 0    | 95    | 7    | 286  | Coffee.Cappuccino | [Milk]
        0     | 300  | 45    | 9    | 16   | Coffee.Cappuccino | [Water]

        and: 'a message indicating such resources are not enough'
        message = "Sorry, not enough resources: $requiredResources!"
    }
}
