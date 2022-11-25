package lv.id.jc.machine.sample.where

import lv.id.jc.machine.exception.NotEnoughResourcesException
import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.unit.impl.StorageBlock
import spock.lang.Specification

class StorageUnitAllocateResourcesSpec extends Specification {

    def 'allocate resources for beverage when the storage is empty'() {

        given:
        def storageUnit = new StorageBlock()

        when:
        storageUnit.allocateResources beverage

        then:
        thrown NotEnoughResourcesException

        where:
        beverage = Coffee.Espresso
    }
}

