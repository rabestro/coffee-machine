package lv.id.jc.machine.unit

interface ControlUnit {
    fun powerOn()
    fun process(request: String)
    fun isOperate(): Boolean
}