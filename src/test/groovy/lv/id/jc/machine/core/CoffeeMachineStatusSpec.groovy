package lv.id.jc.machine.core

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('Technician checks status of coffee machine')
@Narrative('''
As a technician
I want to checks status of coffee machine
So that I can carry out maintenance on the machine
''')
class CoffeeMachineStatusSpec extends Specification {

    def 'should return the coffee machine status'() {

        given: 'coffee machine with a certain amount of ingredients, disposable cups and money'
        def coffeeMachine = new CoffeeMachine(
                new IngredientsUnit(water, milk, beans),
                new DisposableCupsUnit(cups),
                new MoneyUnit(money))

        expect: 'we get a reliable status of the coffee machine'
        coffeeMachine.status() == expected

        where: 'number of ingredients, cups and money'
        water | milk | beans | cups | money
        0     | 0    | 0     | 0    | 0
        5000  | 1000 | 575   | 24   | 120

        and: 'expected coffee machine status report'
        expected = """
            The coffee machine has:
            $water ml of water
            $milk ml of milk
            $beans g of coffee beans
            $cups disposable cups
            \$$money of money
            """.stripIndent().strip()
    }

}
