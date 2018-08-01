# Changelog
All notable changes to this project will be documented in this file.

## [0.1.6] - 2018-08-01
### Added
- new maven module using support of Spring cache abstraction 
- REDIS support added using redisson client (see MultitenantCachingRedissonConfig)
- Multi tenant support works by translating cache item lookup key (see MultitenantCache)
- MultitenantCacheManager will delegate management task to any CacheManager implementation
