package lv.id.jc.machine.sample.mocking

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.StorageUnit
import lv.id.jc.machine.unit.impl.ControlBlock
import spock.lang.Specification

class ProcessFillSpec extends Specification {
    DisplayUnit displayMock = Mock()
    StorageUnit storageMock = Mock()

    def 'process "fill" request'() {

        given:
        def controlUnit = new ControlBlock(displayMock, storageMock)
        controlUnit.powerOn()

        when:
        controlUnit.process Command.FILL.name()

        then:
        1 * displayMock.accept(ControlState.FillWater.prompt)
        0 * _
    }
}

