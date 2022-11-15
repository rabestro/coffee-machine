[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rabestro_coffee-machine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=rabestro_coffee-machine)
# Coffee Machine

The repository contains sample tests written using the Spock Framework. Tests written to solve an educational project from jetbrains Academy.

The [main objective](https://hyperskill.org/projects/67/stages/365/implement) of [the project](https://hyperskill.org/projects/67?track=18) was to use objects to simulate the operation of real devices. It was necessary to create a class with a method that takes a string as input. Below are quotes from the problem statement:

> Every time the user inputs a string to the console, the program invokes this method with one argument: the line that the user inputs to the console. This system simulates pretty accurately how real-world electronic devices work. External components (like buttons on the coffee machine or tapping on the screen) generate events that pass into the single interface of the program.
>
> The class should not use system input at all; it will only handle the input that comes to it via this method and its string argument.
>
> The right solution to this problem is to store the current state of the machine. The coffee machine has several states it can be in. For example, the state could be "choosing an action" or "choosing a type of coffee". Every time the user inputs something and a program passes that line to the method, the program determines how to interpret this line using the information about the current state. After processing this line, the state of the coffee machine can be changed or can stay the same.

In this project, this class is called [Control Unit](https://github.com/rabestro/coffee-machine/blob/main/src/main/kotlin/lv/id/jc/machine/unit/ControlUnit.kt). Requests from users are passed to the class by calling `process` method. The control unit itself controls [storage](https://github.com/rabestro/coffee-machine/blob/main/src/main/kotlin/lv/id/jc/machine/unit/StorageUnit.kt) and display. Schemes of interaction between devices are presented in [wiki](https://github.com/rabestro/coffee-machine/wiki) pages.

## Reports
- [Specifications](https://rabestro.github.io/coffee-machine/)
- [Gradle Test Report](https://rabestro.github.io/coffee-machine/test)
- [Code coverage](https://rabestro.github.io/coffee-machine/jacocoHtml)

## Coffee Machine control state diagram

```mermaid
stateDiagram-v2
    Menu: Write action

    Buy: What do you want to buy?
    state Buy {
        state coffee <<choice>> 
            coffee --> Espresso: 1
            coffee --> Latte : 2
            coffee --> Cappuccino: 3
            coffee --> [*]: back
        [*] --> coffee
    }
    note right of Buy
        I have enough resources, making you a coffee!
    end note
    note right of Buy
        Sorry, not enough water!
    end note
    
    Fill: Replenishment
    state Fill {
        beans: coffee beans
        cups: disposable cups
        [*] --> water
        water --> milk
        milk --> beans
        beans --> cups
        cups --> [*]
    }

    Remaining: Coffe machine report
    note right of Remaining
        The coffee machine has:
        50 ml of water
        465 ml of milk
        100 g of coffee beans
        8 disposable cups
        $557 of money
    end note

    Take: Withdraw money
    note right of Take: I gave you $564

    [*] --> Menu
    Menu --> Buy : buy
    Menu --> Fill: fill
    Menu --> Remaining: remaining 
    Menu --> Take: take
    Menu --> [*] : exit
```
