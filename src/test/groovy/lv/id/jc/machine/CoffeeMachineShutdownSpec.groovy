package lv.id.jc.machine

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.InputUnit
import lv.id.jc.machine.unit.StorageUnit
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.FakeDisplay
import spock.lang.*

@Title('The technician shutdown the coffee machine')
@Narrative('''
As a technician
I want to execute the "exit" command from the main menu 
So that I will be able to shut down the coffee machine
''')
@See('https://github.com/rabestro/coffee-machine/wiki/Coffee-Machine-powerOff')
class CoffeeMachineShutdownSpec extends Specification {

    @Rollup
    def 'should shut down the coffee machine'() {

        given: 'the control unit with fake display and dummy storage unit'
        def fakeDisplay = new FakeDisplay()
        @Subject def controlUnit = new ControlBlock(fakeDisplay, _ as StorageUnit)

        and: 'the coffee machine with stub input'
        def inputUnit = Stub(InputUnit)
        @Subject def coffeeMachine = new CoffeeMachine(inputUnit, controlUnit)

        and: 'the coffee machine has power on'
        coffeeMachine.powerOn()

        expect: 'the coffee machine is operate'
        coffeeMachine.isOperate()

        and: 'the display shows the main menu'
        fakeDisplay.contains ControlState.MainMenu.prompt

        when: 'the technician enters the command to turn off the machine'
        inputUnit.get() >> command

        and: 'the coffee machine processes this command'
        coffeeMachine.processRequest()

        then: 'the coffee machine turns off and no longer functions'
        !coffeeMachine.isOperate()

        where: 'shutdown command'
        command = Command.EXIT.name()
    }
}