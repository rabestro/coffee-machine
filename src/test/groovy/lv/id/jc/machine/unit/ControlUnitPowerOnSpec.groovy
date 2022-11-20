package lv.id.jc.machine.unit


import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Issue
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Tag
import spock.lang.Title

@Title('The technician powers on the coffee machine')
@Narrative('''
As a technician
I want to turn on the coffee machine control unit
so that the coffee machine can serve coffee lovers
''')
@Issue('31')
@See('https://github.com/rabestro/coffee-machine/wiki/Coffee-Machine-powerOn')
@Tag('Sanity')
class ControlUnitPowerOnSpec extends Specification {
    StorageUnit storageStub = Stub()
    DisplayUnit displayMock = Mock()

    def 'should power on the control unit'() {

        given: 'a coffee machine control unit'
        @Subject
        def controlUnit = new ControlBlock(displayMock, storageStub)

        expect: 'control unit is switched off and does not work'
        !controlUnit.isOperate()

        when: 'we turn on the control unit'
        controlUnit.powerOn()

        then: 'the control unit goes into working condition'
        controlUnit.isOperate()

        then: 'the main menu prompt is sent to the display'
        1 * displayMock.accept(ControlState.MainMenu.prompt)
    }
}
