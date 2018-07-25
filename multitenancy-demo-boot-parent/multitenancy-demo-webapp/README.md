# multitenancy-demo-webapp (Multitenancy demo web application)

## Implementation Details
Application developed following the [article](http://anakiou.blogspot.com/2015/08/multi-tenant-application-with-spring.html)


### Features
- Tenant id found from URL
- HTTP interceptor used to extract current tenant id from URL
- Spring AbstractRoutingDataSource feature used to switch database connections defined
- Use YAML property file for tenant configuration
- JPA with Spring data repository model
- 3x PostgreSQL database
- Lombok annotated bean

## TODO
- Change to different namespace for packages to simulate real-world scenario
