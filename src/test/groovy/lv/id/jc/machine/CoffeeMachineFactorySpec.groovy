package lv.id.jc.machine

import lv.id.jc.machine.model.ControlState
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('The developer creates a coffee machine')
@Narrative('''
As a developer
I want to have a fabric method to create a coffee machine
So that I can use the pre-configured coffee machine in the program
''')
class CoffeeMachineFactorySpec extends Specification {

    def 'should create a coffee machine with initial resources'() {

        given: 'standard computer output changed to stub'
        def originalOut = System.out
        def stubOut = new ByteArrayOutputStream()
        System.out = new PrintStream(stubOut)

        when: 'we create a coffee machine using the factory method'
        def coffeeMachine = MainKt.filledCoffeeMachine()

        then: 'we get the coffee machine turned off'
        !coffeeMachine.isOperate()

        when: 'we turn on the coffee machine'
        coffeeMachine.powerOn()

        then: 'we get a working coffee machine'
        coffeeMachine.isOperate()

        and: 'main menu prompt printed to stdout'
        stubOut.toString().contains ControlState.MainMenu.prompt

        cleanup: 'we reset standard output to its initial state'
        System.out = originalOut
    }
}
