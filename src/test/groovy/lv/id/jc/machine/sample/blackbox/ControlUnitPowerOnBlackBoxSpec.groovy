package lv.id.jc.machine.sample.blackbox

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.StorageUnit
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.FakeDisplay
import spock.lang.*

@Title('The technician powers on control unit')
@Narrative('''
As a technician
I want to power on the coffee machine control unit
so that the coffee machine can serve coffee lovers
''')
@See('https://github.com/rabestro/coffee-machine/wiki/Coffee-Machine-powerOn')
@Issue('31')
@Tag('Sanity')
@Subject(ControlBlock)
class ControlUnitPowerOnBlackBoxSpec extends Specification {

    def 'pressing the button on the control unit'() {

        given: 'a control unit'
        def storageStub = Stub(StorageUnit)
        def displayFake = new FakeDisplay()
        def controlUnit = new ControlBlock(displayFake, storageStub)

        and: 'we switch the control unit into one of the control states'
        controlUnit.powerOn()
        requests.each { controlUnit.process(it) }

        expect: 'the control unit is in one of the control states'
        displayFake.lastLine == controlState.prompt

        when: 'we press the power button on the control unit'
        controlUnit.powerOn()

        then: 'the control unit is switched to the primary menu'
        controlUnit.isOperate()
        displayFake.lastLine == ControlState.MainMenu.prompt

        where: 'all possible control states'
        controlState           | requests
        ControlState.MainMenu  | []
        ControlState.BuyCoffee | [Command.BUY.name()]
        ControlState.FillWater | [Command.FILL.name()]
        ControlState.FillMilk  | [Command.FILL.name(), '5000']
        ControlState.FillBeans | [Command.FILL.name(), '5000', '2000']
        ControlState.FillCups  | [Command.FILL.name(), '5000', '2000', '450']
        ControlState.Shutdown  | [Command.EXIT.name()]
    }
}
