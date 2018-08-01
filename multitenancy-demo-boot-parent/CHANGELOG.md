# Changelog
All notable changes to multitenant demonstration projects will be documented in this file.

## [0.1.6] - 2018-08-01
### Added
- new maven module multitenancy-demo-caching
- Embedded Redis server used for local testing
- Maven wrapper setup for demo projects

### Removed
- Maven wrapper files from multitenancy-demo-scheduling

## [0.1.4] - 2017-08-31
### Added
- Multitenant data source switching using AbstractRoutingDataSource
- Multiple tenant configuration details + datasource are read from property file and auto populated into bean
- Bean validation added in controller

### Changed
- Tenant id is now set to thread bound TenantContext, used by MultitenantRoutedDataSource later
- Renamed TenantConfig to TenantProperties

### Removed
- Multitenant JPA configuration and custom implementations

## [0.1.1] - 2017-08-28
### Added
- namespace for multitenancy implementation custom classes
- Necessary comments added to classes with creation date

### Changed
- Multitenancy implementation custom classes moved to new namespace
- tenant identifier key names to understand usage of them

## [0.0.1] - 2017-08-28
### Added
- Multitenancy demo application following the [article](http://anakiou.blogspot.com/2015/08/multi-tenant-application-with-spring.html)
- Spring Boot 1.5.6
- Tenant id found from URL
- HTTP interceptor used to extract current tenant id from URL which is then used by CurrentTenantIdentifierResolverImpl
- MultiTenancyJpaConfiguration switches database connection, defined in DataSourceConfig
- JPA with Spring data repository model
- 3x PostgreSQL database
- Lombok annotations
