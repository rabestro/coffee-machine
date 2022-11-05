package lv.id.jc.machine.unit


import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Title

@Title('The control device queries for the missing coffee drink ingredients')
@Narrative('''
As control unit
I want to know if there are enough ingredients to make a coffee drink
So that I will know if it is possible to prepare a drink
''')
class StorageUnitMissingResourcesSpec extends Specification {

    def 'should return the missing resources for the required drink'() {

        given: 'a storage unit with a certain amount of resources'

        and: 'a control unit with a fake display'

        when: 'we are querying the storage unit about the missing ingredients for making a coffee drink'

        then: 'we get a set containing the missing ingredients'

        where: 'resources in storage device, coffee drink and missing ingredients'

    }
}
