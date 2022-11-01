package lv.id.jc.machine.model


import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Resource)
@Title('Resources for coffee drinks')
class ResourceSpec extends Specification {

    def 'should have the all necessary resources'() {

        given: 'the set of all resource names'
        def names = Resource.values()*.resourceName as Set

        expect: 'the required resource is present in the set of names'
        resource in names

        where: 'a list of resources required for the coffee machine'
        resource << ['water', 'milk', 'coffee beans', 'disposable cups', 'money']
    }
}
