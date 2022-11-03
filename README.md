[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=rabestro_coffee-machine&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=rabestro_coffee-machine)
# Coffee Machine

Implementation of the [educational project](https://hyperskill.org/projects/67?track=18).

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

## Resources replenishment Sequence diagram

```mermaid
sequenceDiagram
    actor Technician
    participant Control Unit
    link Control Unit: source code @ https://github.com/rabestro/coffee-machine/blob/main/src/main/kotlin/lv/id/jc/machine/unit/impl/ControlBlock.kt
    participant Storage Unit
    link Storage Unit: source code @ https://github.com/rabestro/coffee-machine/blob/main/src/main/kotlin/lv/id/jc/machine/unit/StorageUnit.kt
    Control Unit-->>Technician: Write action
    Technician->>+Control Unit: fill
    Control Unit-->>-Technician: How many ml of water?
    Technician->>+Control Unit: 5000
    Control Unit->>+Storage Unit: fill Water, 5000
    Control Unit-->>-Technician: How many ml of milk?
    Technician->>+Control Unit: 3000
    Control Unit->>+Storage Unit: fill Milk, 3000
    Control Unit-->>-Technician: How many grams of coffee beans?
    Technician->>+Control Unit: 1275
    Control Unit->>+Storage Unit: fill CoffeeBeans, 1275
    Control Unit-->>-Technician: How many disposable cups?
    Technician->>+Control Unit: 28
    Control Unit->>+Storage Unit: fill DisposableCaps, 28
    Control Unit-->>Technician: Write action
```