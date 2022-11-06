package lv.id.jc.machine.unit

import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Specification

import static lv.id.jc.machine.model.Resource.Cash
import static lv.id.jc.machine.model.Resource.CoffeeBeans
import static lv.id.jc.machine.model.Resource.DisposableCups
import static lv.id.jc.machine.model.Resource.Milk
import static lv.id.jc.machine.model.Resource.Water

class UnitSpecification extends Specification {

    def storageOf(water, milk, beans, cups, cash = 0) {
        def storageUnit = new StorageBlock()

        with(storageUnit) {
            fill(Water, water)
            fill(Milk, milk)
            fill(CoffeeBeans, beans)
            fill(DisposableCups, cups)
            fill(Cash, cash)
        }

        return storageUnit
    }
}
