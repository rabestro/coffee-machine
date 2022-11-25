package lv.id.jc.machine.sample.whitebox

import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.StorageUnit
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Specification

class ControlUnitPowerOnWhiteBoxSpec extends Specification {
    StorageUnit storage = Stub()
    DisplayUnit display = Stub()

    def 'power on the control unit in state: #state'() {

        given:
        def controlUnit = new ControlBlock(display, storage)
        controlUnit.controlState = state

        when:
        controlUnit.powerOn()

        then:
        controlUnit.isOperate()
        controlUnit.controlState == ControlState.MainMenu

        where:
        state << ControlState.values()
    }
}
