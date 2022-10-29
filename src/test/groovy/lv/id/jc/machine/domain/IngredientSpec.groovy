package lv.id.jc.machine.domain

import lv.id.jc.machine.domain.Ingredient
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Title

@Subject(Ingredient)
@Title('Ingredients for coffee drinks')
class IngredientSpec extends Specification {

    def 'should have the necessary products in the list of ingredients'() {

        given: 'description of all ingredients'
        def ingredients = Ingredient.values()*.description as Set

        expect: 'the required product is present in the list of ingredients'
        product in ingredients

        where: 'a list of products required for the preparation of coffee drinks'
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
