# Project product-hub-spring-boot
The application implements a simple product hub by using Spring Boot.
The main goal of the development is to experiment on the technology used by 
implementing a simple business scenario that can be easily extended. 
Due to this goal, some unrealistic assumptions have been made.

# Business Scenario
- A consumer can send a product request to the product hub so as to buy a product.
A product request does have search criteria such as a search term, a minimum
and a maximum price according to consumer needs.

- A merchant can receive a product request from the product hub so as to sell a
product and can respond with a product response containing the product that better 
suits request's criteria. Instead, a merchant can respond with an empty body if
there is no available product. The product hub can send requests to any merchants 
by calling their api.

- As soon as the request has been sent by a consumer, the product hub should
asynchronously send it to all merchants being recipients of the product request. 
The hub waits all merchants to respond and returns the winner product to a consumer.
The winner is the product having the minimum price among all available products.
The set of merchants can be populated by the product hub based on request's search 
criteria according to any business requirement. However, the set of merchants is 
hard-coded defined in order to simplify the business scenario. 

# Build, Package & Run
- The source code can be built and packaged by using Maven. 
- The spring boot application can run by using maven (use the command [mvn spring-boot:run])
- An executable jar is also generated by packaging the project and its dependencies 
supporting the standalone java execution (use the command java -jar for executable jar file).

# Contribution
- The contribution process is going to be developed...