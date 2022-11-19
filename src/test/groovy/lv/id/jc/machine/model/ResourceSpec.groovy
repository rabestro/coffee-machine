package lv.id.jc.machine.model

import spock.lang.Narrative
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Tag
import spock.lang.Title
import spock.lang.Unroll

@Title('Resources for coffee drinks')
@Narrative('''
As a Coffee Machine
I need required resources
So that I can operate and provide testy coffee drinks
''')
@Tag('Sanity')
@Subject(Resource)
class ResourceSpec extends Specification {

    @Unroll('should have: #requiredResource')
    def 'should have the all necessary resources'() {

        given: 'the set of all resource names'
        def availableResources = Resource.values()*.resourceName

        expect: 'the required resource is present in the set of names'
        availableResources ==~  requiredResource

        where: 'a list of resources required for the coffee machine'
        requiredResource = ['water', 'milk', 'coffee beans', 'disposable cups', 'money']
    }
}
