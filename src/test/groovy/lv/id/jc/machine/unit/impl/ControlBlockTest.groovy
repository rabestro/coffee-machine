package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
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

    def 'should process request "exit"'() {
        when:
        controlUnit.process Command.EXIT.name()

        then:
        displayUnit.text.isBlank()
        !controlUnit.isOperate()
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
