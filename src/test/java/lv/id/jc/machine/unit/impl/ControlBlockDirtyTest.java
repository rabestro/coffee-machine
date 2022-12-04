package lv.id.jc.machine.unit.impl;

import lv.id.jc.machine.model.ControlState;
import lv.id.jc.machine.unit.DisplayUnit;
import lv.id.jc.machine.unit.StorageUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertTrue;

//@Tag("Sanity")
final class ControlBlockDirtyTest {
    private StorageUnit storageUnit;
    private DisplayUnit displayUnit;

    @BeforeEach
    void setUp() {
        storageUnit = Mockito.mock(StorageUnit.class);
        displayUnit = Mockito.mock(DisplayUnit.class);
    }

    @Test
    void powerOn() {
        var controlUnit = new ControlBlock(displayUnit, storageUnit);
        controlUnit.powerOn();
        assertTrue(controlUnit.isOperate(), controlUnit::toString);
        Mockito.verify(displayUnit).accept(ControlState.MainMenu.getPrompt());
    }
}

