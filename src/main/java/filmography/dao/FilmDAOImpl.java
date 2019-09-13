package filmography.dao;

import filmography.model.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FilmDAOImpl implements FilmDAO {

    private SessionFactory sessionFactory;

    //private static final AtomicInteger AUTO_ID = new AtomicInteger(0);
   // private static Map<Integer, Film> films = new HashMap<>();

//    static {
//        Film film1 = new Film();
//        film1.setId(AUTO_ID.getAndIncrement());
//        film1.setTitle("Inception");
//        film1.setYear(2010);
//        film1.setGenre("sci-fi");
//        film1.setWatched(true);
//        films.put(film1.getId(), film1);
//
//        // + film2, film3, film4, ...
//    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Film> allFilms() {
       // return new ArrayList<>(films.values());
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Film").list();
    }

    @Override
    public void add(Film film) {
        //film.setId(AUTO_ID.getAndIncrement());
      //  films.put(film.getId(),film);
    }

    @Override
    public void delete(Film film) {
       // films.remove(film.getId());
    }

    @Override
    public void edit(Film film) {
     //   films.put(film.getId(),film);
    }

    @Override
    public Film getById(int id) {
      //  return films.get(id);
        return null;
    }
}
