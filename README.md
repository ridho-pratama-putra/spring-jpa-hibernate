# How well you can understand the internals of your system by examining its outputs

## Performance oriented Spring Data JPA & Hibernate by Maciej Walkowiak 
    https://www.youtube.com/watch?v=exqfB1WaqIw&t=271s

## TransactionalTemplate
    to start transaction manually to reduce connection leased

## @DynamicUpdate 
    everytime we make changes to update record we should do .save(), but if we dont annotated entity with @DynamicUpdate it will update all fields

## getReferenceById
    in case we need to write record which having relation, we only need its id, not with full object record. this can save hibernate works

## Dynamic projection
    fetch entity only with an intention to modify it. if its only for select, 
    <T> T findById(String id, Class<T> clazz). Because when entity having relation like @ManyToOne, fetching more data than needed & loaded entities stored in jpa persistence context in memory

## @EntityGraph(attributePaths={}) on repository
    to solve n + 1 problems due to fetch entity with relation to another entity

## Conclusion for GET enpoints:
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
    - with code generate latency inside method but AFTER the code required database connection 
    - transaction started manually only on code line require database connection using transactional template
    - only xms connection lease

** result in 5 similar to a function without @Transactional with latency generated after the code line that require database connection

## Distributed Tracing with Spring Cloud Sleuth and Zipkin
    https://medium.com/@bubu.tripathy/distributed-tracing-with-spring-cloud-sleuth-and-zipkin-9106c8afd349
- spring sleuth
- zipkin

## Spring cloud dependency compatibility
    https://spring.io/projects/spring-cloud