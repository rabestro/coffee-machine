package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.unit.DisplayUnit

class FakeDisplay : DisplayUnit {
    var content = ""
    var lastLine = ""

    override fun accept(line: String) {
        lastLine = line
        content += line + System.lineSeparator()
    }

    fun clear() {
        content = ""
    }

    fun contains(value: String) = content.trim().contains(value.trim())
}