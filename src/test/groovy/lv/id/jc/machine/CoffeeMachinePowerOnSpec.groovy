package lv.id.jc.machine

import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.InputUnit
import lv.id.jc.machine.unit.StorageUnit
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Title('The technician power on the coffee machine')
@Narrative('''
As a technician
I want to turn on the coffee machine
so that our customers can enjoy their favorite coffee drinks
''')
class CoffeeMachinePowerOnSpec extends Specification {
    def inputStub = Stub InputUnit
    def storageStub = Stub StorageUnit
    def displayMock = Mock DisplayUnit
    @Subject
    def controlUnit = new ControlBlock(displayMock, storageStub)

    def 'should power on the coffee machine'() {
        given: 'brand new coffee machine'
        @Subject def coffeeMachine = new CoffeeMachine(inputStub, controlUnit)

        expect: 'the machine does not work'
        !coffeeMachine.isOperate()

        when: 'we turn on the coffee machine'
        coffeeMachine.powerOn()

        then: 'the coffee machine goes into working condition'
        coffeeMachine.isOperate()

//        and: 'the main menu prompt is sent on the display'
//        1 * displayMock(ControlState.MainMenu.prompt)
    }

}
