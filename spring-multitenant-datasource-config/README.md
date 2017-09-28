# spring-multitenant-datasource-config (Configure databases for multitenant Spring application)

## Implementation Details
Configures target data sources in Spring _AbstractRoutingDataSource_ for database routing.

Tenant configuration is read from Java properties (__multitenant.properties__) and YAML file (**multitenant.yml**).
Either property / YAML or both can be provided.

Multitenant application need to set CurrentTenant in TenantContextHolder to switch databases automatically.


### Features
- Data source auto configuration from the provided properties or YAML file(s).
- YAML property source factory implementation


## TODO
- Read individual property file for each tenant and setup multitenant environment 
