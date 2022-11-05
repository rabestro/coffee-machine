package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.unit.DisplayUnit

class FakeDisplay : DisplayUnit {
    private var text = ""

    override fun accept(info: String) {
        text += info
    }

    fun clear() {
        text = ""
    }

    fun content() = text
}