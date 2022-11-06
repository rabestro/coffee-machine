package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.FakeDisplay
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('The coffee machine asks control unit for status of resources')
@Narrative('''
As a coffee machine
I want to check remaining resources in storage unit
So that a technician know which resources need to be refilled
''')
class ControlUnitStatusReportSpec extends Specification {

    def 'should return the volume of the remaining resources'() {

        given: 'storage block for a coffee machine'
        def storageUnit = new StorageBlock()

        and: 'the storage is filled with a certain amount of resources'
        with(storageUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Cash, money)
        }

        and: 'a control unit manging the storage and having a fake display'
        def fakeDisplay = new FakeDisplay()
        @Subject def controlUnit = new ControlBlock(fakeDisplay, storageUnit)

        and: 'we switch to main menu mode and clear the display'
        controlUnit.switchTo(ControlState.MainMenu)
        fakeDisplay.clear()

        when: 'we request the current state on the resources of the coffee machine'
        controlUnit.process Command.REMAINING.name()

        then: 'we get on the display detailed information about the state of the resources'
        fakeDisplay.contains report

        where: 'the volume of resources in the storage unit'
        water | milk | beans | cups | money
        0     | 0    | 0     | 0    | 0
        400   | 540  | 20    | 9    | 550

        and: 'expected resource report'
        report << [
                """
                The coffee machine has:
                0 ml of water
                0 ml of milk
                0 g of coffee beans
                0 disposable cups
                \$0 of money
                """,
                """
                The coffee machine has:
                400 ml of water
                540 ml of milk
                20 g of coffee beans
                9 disposable cups
                \$550 of money
                """
        ].collect { it.stripIndent().strip() }
    }
}
