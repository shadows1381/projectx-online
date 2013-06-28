
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
	cache.provider_class='org.hibernate.cache.EhCacheProvider'
}

dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
	// username = "root"
	// password = ""
}

environments {
	
	development {
		dataSource {
			username = "root"
			password = "Arthur43"
			url = "jdbc:mysql://localhost:3306/youthattendance?autoreconnect=true"

			dbCreate = "update" // "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
		}
	}

	production {
		dataSource {			
			// This was used for NDB
			// dialect = org.hibernate.dialect.MySQL5Dialect
			// driverClassName = "com.mysql.jdbc.Driver"
			username = "root"
			password = "Arthur43"
			url = "jdbc:mysql://mysql.online.projectx.co.za:3306/youthattendance?autoreconnect=true"
			
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''

			properties {
				maxActive = 100
				maxIdle = 25
				minIdle = 5
				initialSize = 5
				minEvictableIdleTimeMillis = 60000
				timeBetweenEvictionRunsMillis = 60000
				maxWait = 10000

				testOnBorrow = true
				testWhileIdle = true
				testOnReturn = true

				validationQuery = "select now()"
			}
		}
	}
}