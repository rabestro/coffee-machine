package lv.id.jc.machine.unit

import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.unit.impl.ControlBlock
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.mockito.Mockito

//@Tag("Sanity")
@DisplayName("The technician powers on coffee machine")
internal class ControlUnitPowerOnTest {
    private lateinit var storageUnit: StorageUnit
    private lateinit var displayUnit: DisplayUnit

    @BeforeEach
    fun initMocks() {
        storageUnit = Mockito.mock(StorageUnit::class.java)
        displayUnit = Mockito.mock(DisplayUnit::class.java)
    }

    @Test
    fun `pressing the button on a non-working control unit`() {
        val controlUnit = ControlBlock(displayUnit, storageUnit)

        Assumptions.assumeFalse(controlUnit.isOperate())

        controlUnit.powerOn()

        assertTrue(controlUnit.isOperate(), "control unit does not turn on")
        Mockito.verify(displayUnit).accept(ControlState.MainMenu.prompt)
    }
}

