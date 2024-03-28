# How well you can understand the internals of your system by examining its outputs

## Performance oriented Spring Data JPA & Hibernate by Maciej Walkowiak 
    https://www.youtube.com/watch?v=exqfB1WaqIw&t=271s


## Distributed Tracing with Spring Cloud Sleuth and Zipkin
    https://medium.com/@bubu.tripathy/distributed-tracing-with-spring-cloud-sleuth-and-zipkin-9106c8afd349
- spring sleuth
- zipkin

## Spring cloud dependency compatibility
    https://spring.io/projects/spring-cloud

conclusion for GET enpoints:
1. "book" 
    - no code generate latency
    - only xms connection lease
2. "book/outside/external"
    - with code generate latency but outside of @Transactional method
    - only xms connection lease
3. "book/inside/external"
    - annotated with @Transactional
    - with code generate latency inside method but BEFORE the code required database connection
    - only xms connection lease
4. "book/inside/external/after"
    - annotated with @Transactional
    - with code generate latency inside method but AFTER the code required database connection
    - require xxms (larger) connection lease. 
5. "book/inside/external/after/manual"
    - no transactional annotation 
    - transaction started manually only on code line require database connection using transactional template
    - only xms connection lease

** result in 5 similar to a function without @Transactional with latency generated after the code line that require database connection
