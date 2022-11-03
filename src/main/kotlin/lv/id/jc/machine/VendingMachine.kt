package lv.id.jc.machine

interface VendingMachine {
    fun powerOn()
    fun isOperate(): Boolean
    fun processRequest()
}