package lv.id.jc.machine.unit

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('Technician executes the "fill" command')
@Narrative('''
As a technician
I want to execute the "fill" command in main menu 
So that I will able to replenish the necessary resources in the coffee machine
''')
class ControlUnitFillSpec extends Specification {

    def 'should process the request for filling resources'() {

        given: 'mocked display unit for the coffee machine'
        def displayUnit = Mock DisplayUnit

        and: 'control unit with mocked display and dummy storage'
        @Subject def controlUnit = new ControlBlock(displayUnit, _ as StorageUnit)

        and: 'control unit is in main menu state'
        controlUnit.switchTo ControlState.MainMenu

        when: 'we sends a request for replenishment of resources'
        controlUnit.process Command.FILL.name()

        then: 'coffee machine prompts you to enter the amount of water'
        1 * displayUnit.accept({ it == ControlState.FillWater.prompt })
    }
}
