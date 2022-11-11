package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.tag.UnitTest
import lv.id.jc.machine.unit.ControlUnit
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.StorageUnit
import spock.lang.Specification
import spock.lang.Subject

@UnitTest
@Subject(ControlBlock)
class ControlBlockTest extends Specification {

    DisplayUnit displayUnit = new FakeDisplay()
    StorageUnit storageUnit = Stub()
    ControlUnit controlUnit

    void setup() {
        controlUnit = new ControlBlock(displayUnit, storageUnit)
        controlUnit.powerOn()
        displayUnit.clear()
    }

    void cleanup() {
    }


    def 'should process request "exit"'() {
        when:
        controlUnit.process Command.EXIT.name()

        then:
        displayUnit.text.isBlank()
        !controlUnit.isOperate()
    }

    def loadResources(water, milk, beans, cups, cash) {
        with(storageUnit) {
            volume(Resource.Water) >> water
            volume(Resource.Milk) >> milk
            volume(Resource.CoffeeBeans) >> beans
            volume(Resource.DisposableCups) >> cups
            volume(Resource.Cash) >> cash
        }
    }

    def 'should process request "remaining"'() {

        given:
        loadResources water, milk, beans, cups, cash

        when:
        controlUnit.process Command.REMAINING.name()

        then:
        displayUnit.contains report

        where: 'the volume of resources in the storage unit'
        water | milk | beans | cups | cash
        0     | 0    | 0     | 0    | 0
        400   | 540  | 20    | 9    | 550

        report = """
                The coffee machine has:
                $water ml of water
                $milk ml of milk
                $beans g of coffee beans
                $cups disposable cups
                \$$cash of money
                """.stripIndent()
    }

    def 'should process request "take"'() {

        given:
        loadResources water, milk, beans, cups, cash

        when:
        controlUnit.process Command.TAKE.name()

        then:
        displayUnit.contains report

        where: 'the volume of resources in the storage unit'
        water | milk | beans | cups | cash | report
        0     | 0    | 0     | 0    | 0    | "I gave you \$0"
        400   | 540  | 20    | 9    | 550  | "I gave you \$$cash"
    }

    def 'should process request "fill"'() {
        given:
        loadResources water, milk, beans, cups, cash

        when:
        controlUnit.process Command.FILL.name()

        then:
        displayUnit.contains ControlState.FillWater.prompt

        where: 'the volume of resources in the storage unit'
        water | milk | beans | cups | cash
        0     | 0    | 0     | 0    | 0
        400   | 540  | 20    | 9    | 550
    }


    def 'should process request "buy"'() {
        given:
        loadResources water, milk, beans, cups, cash

        when:
        controlUnit.process Command.BUY.name()

        then:
        displayUnit.contains ControlState.BuyCoffee.prompt

        where: 'the volume of resources in the storage unit'
        water | milk | beans | cups | cash
        0     | 0    | 0     | 0    | 0
        400   | 540  | 20    | 9    | 550
    }

    def "Process"() {

        given:
        def controlUnit = new ControlBlock(displayUnit, storageUnit)

        expect:
        !controlUnit.isOperate()
        displayUnit.text.isEmpty()

        when:
        controlUnit.process(_ as String)

        then:
        displayUnit.text.isEmpty()
        0 * _
    }


    def 'should power on control block'() {

        given:
        def controlUnit = new ControlBlock(displayUnit, storageUnit)

        expect:
        !controlUnit.isOperate()
        displayUnit.text.isEmpty()

        when:
        controlUnit.powerOn()

        then:
        controlUnit.isOperate()
        displayUnit.contains ControlState.MainMenu.prompt
    }

}
