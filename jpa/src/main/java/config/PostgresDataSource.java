package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class PostgresDataSource {

    @Bean
    public DataSource dataSource(Environment env) {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUsername(env.getProperty("postgres.user"));
        ds.setPassword(env.getProperty("postgres.pass"));
        ds.setUrl(env.getProperty("postgres.url"));
        return ds;
    }

    @Bean("dialect")
    public String dialect() {
        return "org.hibernate.dialect.PostgreSQL10Dialect";
    }

}