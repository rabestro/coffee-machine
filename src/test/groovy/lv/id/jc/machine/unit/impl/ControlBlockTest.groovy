package lv.id.jc.machine.unit.impl

import lv.id.jc.machine.model.Command
import lv.id.jc.machine.model.ControlState
import lv.id.jc.machine.model.Resource
import lv.id.jc.machine.tag.UnitTest
import lv.id.jc.machine.unit.ControlUnit
import lv.id.jc.machine.unit.DisplayUnit
import lv.id.jc.machine.unit.StorageUnit
import spock.lang.Specification
import spock.lang.Subject

@UnitTest
class ControlBlockTest extends Specification {
    DisplayUnit displayMock = Mock()
    StorageUnit storageMock = Mock()

    @Subject
    ControlUnit controlUnit = new ControlBlock(displayMock, storageMock)

    def 'should powerOn control block'() {

        expect: 'immediately after creation, the control unit does not work'
        !controlUnit.isOperate()

        when: 'we turn on the control device'
        controlUnit.powerOn()

        then: 'control device goes into operation'
        controlUnit.isOperate()

        and: 'the main menu is displayed on the screen'
        1 * displayMock.accept(ControlState.MainMenu.prompt)
    }

    def 'should process request "exit"'() {

        given: 'we turn on the control device'
        controlUnit.powerOn()

        when:
        controlUnit.process Command.EXIT.name()

        then:
        !controlUnit.isOperate()
    }

    def 'should process request "remaining"'() {

        given:
        StorageUnit storageStub = Stub() {
            volume(Resource.Water) >> water
            volume(Resource.Milk) >> milk
            volume(Resource.CoffeeBeans) >> beans
            volume(Resource.DisposableCups) >> cups
            volume(Resource.Cash) >> cash
        }

        def fakeDisplay = new FakeDisplay()

        and:
        controlUnit = new ControlBlock(fakeDisplay, storageStub)
        controlUnit.powerOn()

        when:
        controlUnit.process Command.REMAINING.name()

        then:
        fakeDisplay.contains report

        where: 'the volume of resources in the storage unit'
        water | milk | beans | cups | cash
        0     | 0    | 0     | 0    | 0
        400   | 540  | 20    | 9    | 550

        report = """
                The coffee machine has:
                $water ml of water
                $milk ml of milk
                $beans g of coffee beans
                $cups disposable cups
                \$$cash of money
                """.stripIndent()
    }

    def 'should process request "take"'() {

        given: 'control block in main menu state'
        controlUnit.powerOn()

        when:
        controlUnit.process Command.TAKE.name()

        then:
        storageMock.volume(Resource.Cash) >> cash

        and:
        1 * storageMock.withdrawCash()

        and:
        1 * displayMock.accept(report)

        where: 'the volume of resources in the storage unit'
        cash << [0, 550, 980]
        report = "I gave you \$$cash"
    }

    def 'should process request "fill"'() {

        given: 'control block in main menu state'
        controlUnit.powerOn()

        when:
        controlUnit.process Command.FILL.name()

        then:
        1 * displayMock.accept(ControlState.FillWater.prompt)
    }

    def 'should process request in "fill water" control mode'() {

        given: 'control block in fill water state'
        controlUnit.powerOn()
        controlUnit.process Command.FILL.name()

        when:
        controlUnit.process request

        then:
        1 * storageMock.fill(Resource.Water, waterVolume)

        and:
        1 * displayMock.accept(ControlState.FillMilk.prompt)

        where:
        request << ['0', '2000', '5000']

        and:
        waterVolume = request.toInteger()
    }

    def 'should process request in "fill milk" control state'() {

        given:
        controlUnit.powerOn()
        controlUnit.process Command.FILL.name()
        controlUnit.process waterVolume

        when:
        controlUnit.process request

        then:
        1 * storageMock.fill(Resource.Milk, volume)

        and:
        1 * displayMock.accept(ControlState.FillBeans.prompt)

        where:
        waterVolume = '9000'
        request << ['0', '2000', '5000']

        and:
        volume = request.toInteger()
    }


    def 'should process request in "fill coffee beans" control state'() {
        given:
        controlUnit.powerOn()

        and:
        controlUnit.process Command.FILL.name()
        controlUnit.process someVolume
        controlUnit.process someVolume

        when:
        controlUnit.process request

        then:
        1 * storageMock.fill(Resource.CoffeeBeans, volume)

        and:
        1 * displayMock.accept(ControlState.FillCups.prompt)

        where:
        someVolume = '9000'
        request << ['0', '2000', '5000']

        and:
        volume = request.toInteger()
    }


    def 'should process request in "fill disposable cups" control state'() {
        given:
        controlUnit.powerOn()
        controlUnit.process Command.FILL.name()
        controlUnit.process someVolume
        controlUnit.process someVolume
        controlUnit.process someVolume

        when:
        controlUnit.process request

        then:
        1 * storageMock.fill(Resource.DisposableCups, volume)

        and:
        1 * displayMock.accept(ControlState.MainMenu.prompt)

        where:
        someVolume = '9000'
        request << ['0', '2000', '5000']

        and:
        volume = request.toInteger()
    }

    def 'should process request "buy"'() {
        given:
        controlUnit.powerOn()

        when:
        controlUnit.process Command.BUY.name()

        then:
        1 * displayMock.accept(ControlState.BuyCoffee.prompt)
    }
}
