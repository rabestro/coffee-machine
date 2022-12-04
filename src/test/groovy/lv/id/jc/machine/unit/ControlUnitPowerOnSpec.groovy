package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.FakeDisplay
import spock.lang.*

@Title('The technician powers on control unit')
@Narrative('''
As a technician
I want to turn on the coffee machine control unit
so that the coffee machine can serve coffee lovers
''')
@Issue('31')
@See('https://github.com/rabestro/coffee-machine/wiki/Coffee-Machine-powerOn')
@Subject(ControlBlock)
@Tag('Sanity')
class ControlUnitPowerOnSpec extends Specification {
    StorageUnit storageStub = Stub()
    DisplayUnit displayMock = Mock()

    def 'pressing the button on a non-working control unit'() {

        given: 'a coffee machine control unit'
        def controlUnit = new ControlBlock(displayMock, storageStub)

        expect: 'control unit is switched off and does not work'
        !controlUnit.isOperate()

        when: 'we press the power button on the control unit'
        controlUnit.powerOn()

        then: 'the control unit goes into working condition'
        controlUnit.isOperate()

        then: 'the main menu prompt is shown on the display'
        1 * displayMock.accept(ControlState.MainMenu.prompt)
    }

    def 'pressing the button on the operating control unit'() {

        given: 'a control unit with fake display and storage'
        def displayFake = new FakeDisplay()
        def controlUnit = new ControlBlock(displayFake, storageStub)

        and: 'we turn on the control unit'
        controlUnit.powerOn()

        and: 'we send a sequence of requests'
        requests.each { controlUnit.process(it) }

        expect: 'the control unit is in one of the operating states'
        controlUnit.isOperate()
        displayFake.lastLine == operatingState.prompt

        when: 'we press the power button on the control unit'
        controlUnit.powerOn()

        then: 'the control unit enters the main menu mode'
        controlUnit.isOperate()

        and: 'the main menu prompt is shown on the display'
        displayFake.lastLine == ControlState.MainMenu.prompt

        where: 'all control statuses except shutdown status'
        operatingState         | requests
        ControlState.MainMenu  | []
        ControlState.BuyCoffee | [Command.BUY.name()]
        ControlState.FillWater | [Command.FILL.name()]
        ControlState.FillMilk  | [Command.FILL.name(), '5000']
        ControlState.FillBeans | [Command.FILL.name(), '5000', '2000']
        ControlState.FillCups  | [Command.FILL.name(), '5000', '2000', '450']
        ControlState.Shutdown  | [Command.EXIT.name()]
    }
}
