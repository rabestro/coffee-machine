package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.unit.DisplayUnit

class FakeDisplay : DisplayUnit {
    private var text = ""

    override fun accept(info: String) {
        text += info + System.lineSeparator()
    }

    fun clear() {
        text = ""
    }

    fun contains(value: String) = text.trim().contains(value.trim())
}