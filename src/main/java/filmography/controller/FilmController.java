package filmography.controller;

import com.sun.org.apache.xpath.internal.operations.Mod;
import filmography.model.Film;
import filmography.service.FilmService;
import filmography.service.FilmServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

//https://javarush.ru/groups/posts/2253-znakomstvo-s-maven-spring-mysql-hibernate-i-pervoe-crud-prilozhenie-chastjh-1

//У Spring MVC есть такая штука, как DispatcherServlet.
// Это как бы главный контроллер, все входящие запросы проходят через него и он уже дальше передает их конкретному контроллеру.
// Аннотация @Controller как раз и сообщает Spring MVC, что данный класс является контроллером
@Controller
public class FilmController {

    private FilmService filmService = new FilmServiceImpl();
    //Аннотация @RequestMapping позволяет задать адреса методам контроллера, по которым они будут доступны в клиенте (браузер).
    // Ее можно применять также и к классу контроллера, чтобы задать, так сказать, корневой адрес для всех методов.
    //Если только получаем данные, то используется GET.
    //Можно использовать сразу аннотацию @GetMapping
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public ModelAndView allFilms(){
        List<Film> films = filmService.allFilms();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("films");
        modelAndView.addObject("filmsList",films);
        return modelAndView;
    }

    //Здесь появилось кое-что новенькое — это аннотация @PathVariable.
    // Она указывает на то, что данный параметр (int id) получается из адресной строки.
    // Чтобы указать место этого параметра в адресной строке используется конструкция {id} (кстати, если имя переменной совпадает,
    // как в данном случае, то в скобках это можно не указывать, а написать просто @PathVariable int id).
    @RequestMapping(value = "/edit/{id}",method = RequestMethod.GET)
    public ModelAndView editPage(@PathVariable("id") int id){
        Film film = filmService.getById(id);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        modelAndView.addObject("film",film);
        return modelAndView;
    }

    //метод для изменения значения (по кнопке "submit" value="Edit film" на editPage.jsp )
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editFilm(@ModelAttribute("film") Film film) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        filmService.edit(film);
        return modelAndView;
    }

    //Стоит также обратить внимание, что у нас оба метода доступны по адресу "/add".
    // Это возможно благодаря тому, что они реагируют на разные типы запроса.
    // Переходя по ссылке на главной странице мы делаем GET-запрос, что приводит нас в метод addPage.
    // А когда на странице добавления мы жмем кнопку отправки данных, делается POST-запрос, за это уже отвечает метод addFilm.

    //метод для получения страницы
    @RequestMapping(value = "/add",method = RequestMethod.GET)
    public ModelAndView addPage(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editPage");
        return modelAndView;
    }

    //метод для добавления значения (по кнопке "submit" value="Add film" на editPage.jsp )
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ModelAndView addFilm(@ModelAttribute("film") Film film) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        filmService.add(film);
        return modelAndView;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    public ModelAndView deleteFilm(@PathVariable("id") int id){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        filmService.delete(filmService.getById(id));
        return modelAndView;
    }

}
