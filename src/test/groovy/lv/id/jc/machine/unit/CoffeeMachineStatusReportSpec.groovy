package lv.id.jc.machine.unit

import lv.id.jc.machine.state.MainMenu
import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

import static lv.id.jc.machine.model.Resource.*

@Title('Technician checks remaining resources in coffee machine')
@Narrative('''
As a technician
I want to checks remaining resources in coffee machine
So that I know which resources need to be refilled
''')
@Subject(Engine)
class CoffeeMachineStatusReportSpec extends Specification {

    def 'should return the volume of the remaining resources'() {

        given: 'coffee machine with initial values'
        def machine = new Engine()
        def menu = new MainMenu(machine)

        expect: 'the initial volume of all resources is zero'
        values().every { machine.volume(it) == 0 }

        when: 'we add a certain amount of resources'
        with(machine) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Money, money)
        }

        then: 'we get an accurate report on the amount of resources available'
        menu.status() == report

        where: 'the volume of resources'
        water | milk | beans | cups | money
        0     | 0    | 0     | 0    | 0
        400   | 540  | 20    | 9    | 550

        and: 'expected resource report'
        report << [
                """
                The coffee machine has:
                0 ml of water
                0 ml of milk
                0 g of coffee beans
                0 disposable cups
                \$0 of money
                """,
                """
                The coffee machine has:
                400 ml of water
                540 ml of milk
                20 g of coffee beans
                9 disposable cups
                \$550 of money
                """
        ].collect { it.stripIndent().strip() }
    }
}
