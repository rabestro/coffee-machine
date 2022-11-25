package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.exception.NotEnoughResourcesException
import lv.id.jc.machine.model.Coffee
import lv.id.jc.machine.model.Resource
import spock.lang.Specification

class StorageBlockAllocateResourcesSpec extends Specification {

    def 'allocation of resources when the storage is empty'() {
        given:
        def storageUnit = new StorageBlock()

        expect:
        Resource.values()
                .every { storageUnit.volume(it) == 0 }

        when:
        storageUnit.allocateResources Coffee.Espresso

        then:
        thrown NotEnoughResourcesException
    }
}
