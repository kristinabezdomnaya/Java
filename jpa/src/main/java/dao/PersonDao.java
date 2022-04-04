package dao;

import model.Person;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class PersonDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void save(Person person) {
        if (person.getId() == null) {
            em.persist(person);
        } else {
            em.merge(person);
        }
    }

    public List<Person> findAll() {
        return em.createQuery("select p from Person p")
                .getResultList();
    }

    public Person findPersonByName(String name) {
        TypedQuery<Person> query = em.createQuery(
                "select p from Person p where p.name = :name",
                Person.class);

        query.setParameter("name", name);

        return query.getSingleResult();
    }
}
