# 'Live Order Board' Rest Service

`order-service` is a REST microservice application.

## Implementation

Following tech spec is used for the TDD based implementation.

- *Java*
- *Spring Boot*
- *maven*
- *JUnit*
- *h2*

The project is organized as a *maven* project and in order to compile, test and create a package *maven* is used.

### Building the application

You could use maven to test and build the jar file.

* In the root directory of the project run the following commands

```bash
# Compile
mvn -B clean compile

#Test
mvn -B clean test


#Create the package
mvn -B clean package

```

## Using the API

### Running the application

* Start the application with the following command:

```bash

#Under the root folder of the project

java -jar target/order-service-1.0-SNAPSHOT.jar

```


### Request

The endpoint of the application as given in the following table.

1) Register an order

|End Point                      | Operation    |Port  |
|-------------------------------|--------------|------|
|http://localhost:8080/orders/  |POST          | 8080 |


* Sample Order Request
```json

{
  "userId" : "user2",
  "quantity" : 22.1,
  "price" : 100,
  "orderType" : "SELL"
}

```

2) Cancel a registered order

|End Point                         | Operation    |Port  |
|----------------------------------|--------------|------|
|http://localhost:8080/orders/{id} |DELETE        | 8080 |


3) Get summary information of live orders

|End Point                                                 | Operation    |Port  |
|----------------------------------------------------------|-----------|------|
|http://localhost:8080/orders/search?orderType={OrderType} |GET        | 8080 |


OrderType :
    - SELL
    - BUY

* Sample Order Response for SELL
```json

[{
  "price" : 100,
  "totalQuantity": 22.1
},
{
  "price" : 200,
  "totalQuantity": 44.1
},
]
```

* Sample Order Response for BUY
```json

[{
  "price" : 200,
  "totalQuantity": 44
},
{
  "price" : 100,
  "totalQuantity": 66
},
]
```
## Database

TABLE NAME : ORDERS

 |Column Name      | Type                | Not Null |
 |-----------------|---------------------|----------|
 |ID               | BIGINT              | Y        |
 |ORDER_TYPE       | INTEGER             | Y        |
 |PRICE            | BIGINT              | Y        |
 |QUANTITY         | DOUBLE              | Y        |
 |USER_ID          | VARCHAR(255)        | Y        |
