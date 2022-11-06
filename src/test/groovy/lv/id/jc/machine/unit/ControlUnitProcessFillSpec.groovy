package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Narrative
import spock.lang.See
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('The coffee machine send the "fill" command')
@Narrative('''
As a coffee machine
I want the control block to process the "fill" command
So that I will able to replenish the necessary resources in the storage unit
''')
@See('https://github.com/rabestro/coffee-machine/wiki/Resources-replenishment')
class ControlUnitProcessFillSpec extends Specification {

    def 'should process the request for filling resources'() {

        given: 'mock of display unit for the coffee machine'
        def displayUnit = Mock DisplayUnit

        and: 'control unit with mock display and dummy storage'
        @Subject def controlUnit = new ControlBlock(displayUnit, _ as StorageUnit)

        and: 'control unit is in the main menu state'
        controlUnit.switchTo ControlState.MainMenu

        when: 'coffee machine sends a request to control unit'
        controlUnit.process Command.FILL.name()

        then: 'the display prompts you to enter the volume of water'
        1 * displayUnit.accept({ it == ControlState.FillWater.prompt })
    }
}
