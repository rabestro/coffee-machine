package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.StorageUnit
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito

@Tag("Sanity")
@DisplayName("The technician power on the control unit")
internal class ControlUnitPowerOnTest {
    private lateinit var storageUnit: StorageUnit
    private lateinit var displayUnit: DisplayUnit

    @BeforeEach
    fun initMocks() {
        storageUnit = Mockito.mock(StorageUnit::class.java)
        displayUnit = Mockito.mock(DisplayUnit::class.java)
    }

    @Test
    @DisplayName("should power on the control unit")
    fun powerOn() {
        val controlUnit = ControlBlock(displayUnit, storageUnit)

        Assumptions.assumeFalse(controlUnit.isOperate())

        controlUnit.powerOn()

        assertTrue(controlUnit.isOperate(), "control unit does not turn on")
        Mockito.verify(displayUnit).accept(ControlState.MainMenu.prompt)
    }
}

