package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.unit.DisplayUnit

class FakeDisplay : DisplayUnit {
    var text = ""

    override fun accept(info: String) {
        text += info
    }
}