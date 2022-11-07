package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('The coffee machine switches off the control unit')
@Narrative('''
As a coffee machine
I want to turns off the control unit
So that it stops working and the coffee machine turns off
''')
@See('https://github.com/rabestro/coffee-machine/wiki/Coffee-Machine-powerOff')
class ControlUnitShutdownSpec extends Specification {

    def 'should turn off the control unit'() {

        given: 'the control unit with dummy display and storage'
        def displayUnit = Stub DisplayUnit
        @Subject def controlUnit = new ControlBlock(displayUnit, _ as StorageUnit)

        and: 'the control unit is in main menu state'
        controlUnit.switchTo ControlState.MainMenu

        expect: 'the control unit works'
        controlUnit.isOperate()

        when: 'the coffee machine sends a request'
        controlUnit.process request

        then: 'the control unit is no more operate'
        !controlUnit.isOperate()

        where: 'request to turn off the coffee machine'
        request = Command.EXIT.name()
    }
}
