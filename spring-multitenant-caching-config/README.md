# spring-multitenant-caching-config (Configure caching for multitenant Spring application)

## Implementation Details
Current implementation provides support for multi-tenancy by translating the lookup keys into a tenant-context-specific key.

### Features
- REDIS spring cache manager is supported using redisson client

## TODO
- Provide implementation to get tenant specific cache (namespaced cache names)
- Separate cache manager instance per tenant is not possible.
