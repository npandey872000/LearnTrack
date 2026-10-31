# Chain of Responsibility

This small console example demonstrates the **Chain of Responsibility** design pattern in Java.

The pattern passes a request along a chain of handlers. Each handler either deals with the request or forwards it to the next handler. The sender only needs to know the first handler, so it does not need to choose a specific handler itself.

## Example

The example models leave requests:

- A **Team Lead** can approve up to 2 days.
- A **Manager** can approve up to 5 days.
- A **Director** can approve up to 10 days.
- Requests above 10 days are declined.

The handlers are connected in that order. Each class contains a comment explaining its role.

## Requirements

- Java 8 or later

## Run

From this directory, compile and run the example:

```sh
javac -d out src/main/java/learntrack/patterns/chainofresponsibility/*.java
java -cp out learntrack.patterns.chainofresponsibility.ChainOfResponsibilityDemo
```

The output shows which handler receives each request and the decision it makes.

## Main idea

1. A client creates a request and sends it to the first handler.
2. A handler checks whether it can handle the request.
3. If it cannot, it passes the request to the next handler.
4. The chain ends when a handler handles the request or no handler remains.

This keeps the client independent of the approval rules and makes it straightforward to add or reorder handlers.
