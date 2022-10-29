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
        product in ingredients

        where: 'three main ingredients are'
        product << ['water', 'milk', 'coffee beans']
    }

    def 'should set units of measurement for each of the ingredients'() {

        when: 'we find the ingredient by its description'
        def ingredient = Ingredient.values().find { it.description == product }

        then: 'ingredient should be found'
        ingredient

        and: 'the correct unit of measurement is set for the ingredient'
        ingredient.unit == expected

        where:
        product        | expected
        'water'        | 'ml'
        'milk'         | 'ml'
        'coffee beans' | 'gr'
    }
}
