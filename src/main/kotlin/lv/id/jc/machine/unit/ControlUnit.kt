package lv.id.jc.machine.unit

interface ControlUnit {
    fun powerOn()
    fun isOperate(): Boolean
    fun process(request: String)
}
