package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('Technician turns off the coffee machine')
@Narrative('''
As a technician
I want to turns off the coffee machine
So that it stops working and I can do maintenance
''')
class ControlUnitShutdownSpec extends Specification {

    def 'should turn off the control unit'() {

        given: 'coffee machine control unit with dummy display and storage'
        @Subject def controlUnit = new ControlBlock(Stub(DisplayUnit), _ as StorageUnit)

        and: 'control unit is in main menu state'
        controlUnit.switchTo(ControlState.MainMenu)

        expect: 'control unit works'
        controlUnit.isOperate()

        when: 'the technician sends a command to turn off the machine'
        controlUnit.process request

        then: 'the control unit switches off and the coffee machine no longer works'
        !controlUnit.isOperate()

        where: 'command to turn off the coffee machine'
        request = Command.EXIT.name()
    }
}
