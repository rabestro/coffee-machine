package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.FakeDisplay
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('The collector withdraws cash for sold coffee drinks')
@Narrative('''
As a collector
I want to withdraws cash for sold coffee drinks
So that I can bring the collected cash to the accountant
''')
@See('https://github.com/rabestro/coffee-machine/wiki/Withdraw-Cash')
class ControlUnitWithdrawCashSpec extends UnitSpecification {

    def 'should withdraw cash from storage unit'() {

        given: 'storage unit with a particular volume of resources'
        @Subject def storageUnit = storageOf water, milk, beans, cups, cash

        and: 'control unit with the storage and fake display'
        @Subject def controlUnit = new ControlBlock(fakeDisplay, storageUnit)

        and: 'control unit is in the main menu state'
        controlUnit.switchTo ControlState.MainMenu

        and: 'we clear all messages from fake display'
        fakeDisplay.clear()

        when: 'the collector sends a request to withdraw cash from the coffee machine'
        controlUnit.process request

        then: 'the display shows conformation message'
        fakeDisplay.contains message

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
