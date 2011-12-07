hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}
// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:devDb"
			pooled = true
			driverClassName = "org.hsqldb.jdbcDriver"
			username = "sa"
			password = ""
        }
    }
    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:mem:testDb"
			pooled = true
			driverClassName = "org.hsqldb.jdbcDriver"
			username = "sa"
			password = ""
        }
    }
    production {
        dataSource {
            dbCreate = "update"
            url = "jdbc:hsqldb:file:prodDb;shutdown=true"
        }
    }
}
