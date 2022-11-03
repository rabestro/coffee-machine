package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('Collector withdraws cash for sold coffee drinks')
@Narrative('''
As a collector
I want to withdraws cash for sold coffee drinks
So that I can bring the collected cash to the accountant
''')
class ControlUnitWithdrawCashSpec extends Specification {

    def 'should turn off the control unit of the coffee machine'() {

        given: 'storage unit with a particular volume of resources'
        @Subject def storageUnit = new StorageBlock()

        with(storageUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Cash, cash)
        }

        and: 'mocked display unit'
        def display = Mock(DisplayUnit)

        and: 'control unit with the storage and mocked display'
        @Subject def controlUnit = new ControlBlock(display, storageUnit)

        and: 'control unit is in main menu state'
        controlUnit.switchTo(ControlState.MainMenu)

        when: 'the collector sends a request to withdraw cash from the coffee machine'
        controlUnit.process request

        then: 'the display shows conformation message'
        1 * display.accept({ it == message })

        and: 'the cash container is empty while other at their original state'
        with(storageUnit) {
            volume(Cash) == 0
            volume(Water) == water
            volume(Milk) == milk
            volume(CoffeeBeans) == beans
            volume(DisposableCups) == cups
        }

        where: 'resources in the storage block'
        water | milk | beans | cups | cash
        0     | 0    | 0     | 0    | 0
        400   | 540  | 120   | 9    | 550
        0     | 270  | 75    | 3    | 1027

        and: 'command to withdraw cash from the coffee machine'
        request = Command.TAKE.name()

        and: 'confirmation message'
        message = "I gave you \$$cash"
    }
}
