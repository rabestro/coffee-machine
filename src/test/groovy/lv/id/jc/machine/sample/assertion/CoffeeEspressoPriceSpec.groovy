package lv.id.jc.machine.sample.assertion

import lv.id.jc.machine.model.Coffee
import spock.lang.Specification

class CoffeeEspressoPriceSpec extends Specification {

    def 'should be fair price for Espresso'() {

        expect:
        Coffee.Espresso.price == 4
    }
}
