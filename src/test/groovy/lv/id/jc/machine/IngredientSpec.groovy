package lv.id.jc.machine


import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Ingredient)
@Title('Ingredients needed to make coffee')
class IngredientSpec extends Specification {

    def 'should have all main ingredients'() {

        given: 'description of all ingredients as a set'
        def ingredients = Ingredient.values()*.description as Set

        expect: 'required ingredient is presented'
        ingredient in ingredients

        where: 'three main ingredients are'
        ingredient << ["water", "milk", "coffee beans"]
    }

    def 'should set units of measurement for each of the ingredients'() {

        expect: 'the correct unit of measurement is set for each of the ingredients'

    }
}
