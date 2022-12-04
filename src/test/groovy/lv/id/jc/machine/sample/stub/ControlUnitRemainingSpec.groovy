package lv.id.jc.machine.sample.stub

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.unit.StorageUnit
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.FakeDisplay
import spock.lang.Specification
import spock.lang.Subject

import static lv.id.jc.machine.model.Resource.*

class ControlUnitRemainingSpec extends Specification {

    def 'should return the volume of the remaining resources'() {

        given:
        def storageUnit = Stub(StorageUnit) {
            volume(Water) >> water
            volume(Milk) >> milk
            volume(CoffeeBeans) >> beans
            volume(DisposableCups) >> cups
            volume(Cash) >> money
        }
        and:
        def fakeDisplay = new FakeDisplay()
        and:
        @Subject
        def controlUnit = new ControlBlock(fakeDisplay, storageUnit)

        and:
        controlUnit.powerOn()
        fakeDisplay.clear()

        when:
        controlUnit.process Command.REMAINING.name()

        then:
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
        ].collect { it.stripIndent() }
    }
}
