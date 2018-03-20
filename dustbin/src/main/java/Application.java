import com.iot.rest.JsonPostgreSQLDialect;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.ConnectionReleaseMode;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Environment;
import org.hibernate.stat.Statistics;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaDialect;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.SharedCacheMode;
import javax.sql.DataSource;
import java.util.Properties;


@ComponentScan(basePackages = { "com.iot" })
@EnableAutoConfiguration
@SpringBootApplication
@EnableConfigurationProperties
@Configuration
@EnableTransactionManagement
public class Application {

    private static final String SHOW_SQL = "hibernate.show_sql";
    private static final String FORMAT_SQL = "hibernate.format_sql";

    private int getMaxPoolSize() {
        return  6;
    }

    private String getUsername() {
        return  "{username}";
    }

    private String getPassword() {
        return "{password}";
    }

    private long getConnectionTimeout() {
        return 10000;
    }

    private String getDatabaseName() {
        return "{dbName}";
    }

    private String getServername() {
        return "serverName";
    }

    private int getPortNumber() {
        return 5432;
    }

    private Boolean getShowSql() {
        return false;
    }

    private Boolean getFormatSql() {
        return true;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Application Started.");

        };
    }


    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        ds.addDataSourceProperty("databaseName", getDatabaseName());
        ds.addDataSourceProperty("serverName", getServername());
        ds.addDataSourceProperty("portNumber", 5432);
        ds.setUsername(getUsername());
        ds.setPassword(getPassword());
        ds.setConnectionTimeout(getConnectionTimeout());
        ds.setMaximumPoolSize(getMaxPoolSize());
        //ds.setLeakDetectionThreshold(5000);
        return ds;
    }

    @Bean(destroyMethod="")
    public EntityManagerFactory entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setSharedCacheMode(SharedCacheMode.ENABLE_SELECTIVE);
        em.setDataSource(dataSource());
        em.setJpaDialect(new HibernateJpaDialect());
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setPackagesToScan("com.mwyn");
        em.setJpaProperties(hibernateProperties());
        em.afterPropertiesSet();
        return em.getObject();
    }

    @Bean
    public Properties hibernateProperties(){
        Properties properties = new Properties();
        properties.put(Environment.DIALECT, JsonPostgreSQLDialect.class.getName());
        properties.put(Environment.SHOW_SQL, getShowSql());
        properties.put(Environment.FORMAT_SQL, getFormatSql());
        properties.put(Environment.USE_SQL_COMMENTS, false);
        properties.put(Environment.GENERATE_STATISTICS, false);
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, SpringSessionContext.class.getName());
        properties.put(Environment.ENABLE_LAZY_LOAD_NO_TRANS, true);
        properties.put(Environment.RELEASE_CONNECTIONS, ConnectionReleaseMode.AFTER_TRANSACTION);
        return properties;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
        jpaTransactionManager.setGlobalRollbackOnParticipationFailure(false);
        return jpaTransactionManager;
    }

    @Bean
    public Statistics statistics(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return sessionFactory(emf).getStatistics();
    }

    @Primary
    @Bean
    public SessionFactory sessionFactory(@Qualifier("entityManagerFactory") EntityManagerFactory emf) {
        return emf.unwrap(SessionFactory.class);
    }



}
