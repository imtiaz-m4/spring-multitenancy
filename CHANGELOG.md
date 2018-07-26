# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]
Un-released changes are tracked here.

## [0.1.5] - 2018-07-26
### Added
- spring-multitenant-datasource-config now provides org.yaml.snakeyaml dependency

### Changed
- revision variable is creating issue, reverted back to using constants
- README in spring-multitenant-datasource-config now contains usage section
- Fixed javadoc issues
- Package namespace of demo apps modified with prefix `com.metafour.multitenancy.demo.`

### Removed
- CHANGELOG.md in multitenancy-context, use the CHANGELOG.md in parent project
- Not needed MultitenantDatasourceAutoConfigure in spring-multitenant-datasource-config

## [0.1.3] - 2018-07-25
### Added
- TenantLookupFailureException in multitenancy-context for future use
- multitenant sample property and YML files in datasource config test

### Changed
- Upgraded maven wrapper to v3.5.4
- Project version (revision) is now available as variable and supported by Maven 3.5.0+
- TenantContextHolder changes: getCurrentTenantId and clearCurrentTenant
- SpringProfileDocumentMatcher not needed in YamlPropertySourceFactory

## [0.1.1] - 2018-07-17
### Added
- missing maven-wrapper.properties
- Blank project structure

### Changed
- Maven plugins and lombok version bumped


## [version] - 2017-06-20
### Added [new features]
### Changed [changes in existing functionality]
### Deprecated [soon-to-be removed features]
### Removed [removed features]
### Fixed [bug fixes]
### Security [vulnerabilities]
