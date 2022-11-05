package lv.id.jc.machine

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.InputUnit
import lv.id.jc.machine.unit.impl.ControlBlock
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.*

@Title('The technician replenish resources in the coffee machine')
@Narrative('''
As a technician
I want to replenish the necessary resources in the coffee machine
So that the coffee machine can prepare the necessary coffee drinks
''')
@See('https://github.com/rabestro/coffee-machine/wiki/Resources-replenishment')
class CoffeeMachineReplenishSpec extends Specification {

    def 'should replenish the coffee machine storage unit'() {

        given: 'the coffee machine terminal is mock'
        def inputUnit = Stub(InputUnit) {
            get() >> command >> water >> milk >> beans >> cups
        }
        def displayUnit = Mock(DisplayUnit)

        and: 'the coffee machine, control and storage units are subjects under specification'
        @Subject def storageUnit = new StorageBlock()
        @Subject def controlUnit = new ControlBlock(displayUnit, storageUnit)
        @Subject def coffeeMachine = new CoffeeMachine(inputUnit, controlUnit)

        expect: 'immediately after creating the coffee machine does not work'
        !coffeeMachine.isOperate()

        when: 'we turn on the coffee machine'
        coffeeMachine.powerOn()

        then: 'the coffee machine goes into working condition'
        coffeeMachine.isOperate()

        and: 'the main menu prompt appears on the display'
        1 * displayUnit.accept(ControlState.MainMenu.prompt)

        when: 'the coffee machine processes the very first request'
        coffeeMachine.processRequest()

        then: 'a message appears on the display asking you to enter the volume of water'
        1 * displayUnit.accept(ControlState.FillWater.prompt)

        when: 'the coffee machine processes the entered amount of water'
        coffeeMachine.processRequest()

        then: 'a message appears on the display asking you to enter the volume of milk'
        1 * displayUnit.accept(ControlState.FillMilk.prompt)

        when: 'the coffee machine processes the entered amount of milk'
        coffeeMachine.processRequest()

        then: 'a message appears on the display asking you to enter the number of coffee beans'
        1 * displayUnit.accept(ControlState.FillBeans.prompt)

        when: 'the coffee machine processes the entered amount of coffee beans'
        coffeeMachine.processRequest()

        then: 'a message appears on the display asking you to enter the number of disposable cups'
        1 * displayUnit.accept(ControlState.FillCups.prompt)

        when: 'the coffee machine processes the entered number of disposable cups'
        coffeeMachine.processRequest()

        then: 'storage container replenished with the specified amount of resources'
        with(storageUnit) {
            volume(Resource.Water) == water
            volume(Resource.Milk) == milk
            volume(Resource.CoffeeBeans) == beans
            volume(Resource.DisposableCups) == cups
        }

        and: 'the main menu prompt reappears on the coffee machine display'
        1 * displayUnit.accept(ControlState.MainMenu.prompt)

        where: 'the volume of resources to replenish in coffee machine'
        water | milk | beans | cups
        0     | 0    | 0     | 0
        400   | 540  | 20    | 9
        5470  | 4323 | 1123  | 34

        and: 'command to start the process of replenishing the resources of the coffee machine'
        command = Command.FILL.name()
    }
}
