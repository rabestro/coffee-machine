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
    participant CoffeeMachine
    participant ControlUnit
    link ControlUnit: source code @ https://github.com/rabestro/coffee-machine/blob/main/src/main/kotlin/lv/id/jc/machine/unit/impl/ControlBlock.kt
    
    participant StorageUnit
    link StorageUnit: source code @ https://github.com/rabestro/coffee-machine/blob/main/src/main/kotlin/lv/id/jc/machine/unit/StorageUnit.kt
    
    ControlUnit-->>CoffeeMachine: Write action
    CoffeeMachine->>+ControlUnit: process "fill"
    ControlUnit-->>-CoffeeMachine: How many ml of water?
    CoffeeMachine->>+ControlUnit: process 5000
    ControlUnit->>+StorageUnit: fill Water, 5000
    ControlUnit-->>-CoffeeMachine: How many ml of milk?
    CoffeeMachine->>+ControlUnit: process 3000
    ControlUnit->>+StorageUnit: fill Milk, 3000
    ControlUnit-->>-CoffeeMachine: How many grams of coffee beans?
    CoffeeMachine->>+ControlUnit: process 1275
    ControlUnit->>+StorageUnit: fill CoffeeBeans, 1275
    ControlUnit-->>-CoffeeMachine: How many disposable cups?
    CoffeeMachine->>+ControlUnit: process 28
    ControlUnit->>+StorageUnit: fill DisposableCaps, 28
    ControlUnit-->>CoffeeMachine: Write action
```

