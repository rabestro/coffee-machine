package lv.id.jc.machine.unit

import lv.id.jc.machine.model.ControlState
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import java.util.function.Consumer

import static lv.id.jc.machine.model.Resource.*

@Title('Technician checks remaining resources in coffee machine')
@Narrative('''
As a technician
I want to checks remaining resources in coffee machine
So that I know which resources need to be refilled
''')
class CoffeeMachineStatusReportSpec extends Specification {

    def 'should return the volume of the remaining resources'() {

        given: 'storage block for a coffee machine'
        def storageBlock = new StorageBlock()

        and: 'the storage is filled with a certain amount of resources'
        with(storageBlock) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Cash, money)
        }

        and: 'dummy display implementation'
        def fakeDisplay = new Consumer<String>() {
            def text = ""

            @Override
            void accept(String output) {
                text += output
            }
        }

        and: 'a control device having a fake display and managing a resource block'
        @Subject def controlBlock = new ControlBlock(fakeDisplay, storageBlock)

        and: 'we switch the control unit to main menu mode'
        controlBlock.switchTo(ControlState.MainMenu)

        and: 'we clean the fake display'
        fakeDisplay.text = ""

        when: 'we request the current state on the resources of the coffee machine'
        controlBlock.process("remaining")

        then: 'we get on the display detailed information about the state of the resources'
        fakeDisplay.text == report

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
