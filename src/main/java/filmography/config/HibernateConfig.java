package filmography.config;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

/*С @Configuration и @ComponentScan уже разобрались когда делали класс WebConfig.
@EnableTransactionManagement — позволяет использовать TransactionManager для управления транзакциями.
Hibernate работает с БД с помощью транзакций, они нужны чтобы какой-то набор операций выполнялся как единое целое,
т.е. если в методе возникнут проблемы с какой-то одной операцией, тогда не выполнятся и все остальные,
чтобы не было как в классическом примере с переводом денег, когда операция снятия денег
с одного счета свершилась, а операция записи на другой не сработала, в итоге деньги исчезли.
@PropertySource — подключение файла свойств, который мы недавно создавали.
Environment — для того, чтобы получить свойства из property файла.
hibernateProperties — этот метод нужен чтобы представить свойства Hibernate в виде объекта Properties
DataSource — используется для создания соединения с БД. Это альтернатива DriverManager,
которой мы использовали ранее, когда создавали для проверки подключения метод main.
В документации сказано, что DataSource использовать предпочтительнее.
Так и поступим, естественно не забыв почитать в интернете в чем разница и преимущества.
В частности, одним из преимуществ является возможность создания пула соединений Database Connection Pool (DBCP).
sessionFactory — для создания сессий, с помощью которых осуществляются операции с объектами-сущностями.
Здесь мы устанавливаем источник данных, свойства Hibernate и в каком пакете нужно искать классы-сущности.
transactionManager — для настройки менеджера транзакций.*/

@Configuration
@ComponentScan(basePackages = "filmography")
@EnableTransactionManagement
@PropertySource(value = "classpath:db.properties")
public class HibernateConfig {
    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        return properties;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
        return dataSource;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan("filmography.model");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}