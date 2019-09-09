package filmography.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

//@Configuration сообщает Spring что данный класс является конфигурационным и содержит определения и зависимости bean-компонентов.
// Бины (bean) — это объекты, которые управляются Spring'ом. Для определения бина используется аннотация @Bean.

//@EnableWebMvc позволяет импортировать конфигурацию Spring MVC из класса WebMvcConfigurationSupport.
// Можно также реализовать, например, интерфейс WebMvcConfigurer, у которого есть целая куча методов,
// и настроить все по своему вкусу, но нам незачем пока в это углубляться, хватит и стандартных настроек.

//@ComponentScan сообщает Spring где искать компоненты, которыми он должен управлять, т.е. классы,
// помеченные аннотацией @Component или ее производными, такими как @Controller, @Repository, @Service.
// Эти аннотации автоматически определяют бин класса.

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "filmography") //без этой аннотации не взлетало
public class WebConfig {

    //В методе viewResolver() мы создаем его реализацию и определяем где именно искать представления в webapp.
    // Поэтому когда в методе контроллера мы устанавливали имя "films" представление найдется как "/pages/films.jsp"

    @Bean
    ViewResolver viewResolver(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/"); //нужно засунусть view в директорию WEB-INF,чтобы все обращения были только через контроллер.Иначе можно было напряму обратиться
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
