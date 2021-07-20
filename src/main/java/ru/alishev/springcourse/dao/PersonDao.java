package ru.alishev.springcourse.dao;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.alishev.springcourse.models.Person;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@Component
public class PersonDao {

    @PersistenceContext
    private EntityManager entityManager;


    public List<Person> index() {
        return entityManager.createQuery("SELECT p FROM Person p", Person.class).getResultList();
    }

    public Person show(int id) {
        TypedQuery<Person> query = entityManager.createQuery("SELECT p FROM Person p where p.id = : id", Person.class);
        query.setParameter("id", id);
        return query.getResultList().stream().findAny().orElse(null);
    }

    public void save(Person person) {
        entityManager.persist(person);
    }

    public void update(int id, Person updatedPerson) {
        Person personToBeUpdated = show(id);

        personToBeUpdated.setName(updatedPerson.getName());
        personToBeUpdated.setAge(updatedPerson.getAge());
        personToBeUpdated.setEmail(updatedPerson.getEmail());

        entityManager.createQuery("update Person p set p.name =: name, p.age =: age, p.email =: email where p.id=:id")
                .setParameter("id", id)
                .setParameter("name", updatedPerson.getName())
                .setParameter("age", updatedPerson.getAge())
                .setParameter("email", updatedPerson.getEmail()).executeUpdate();
    }

    public void delete(int id) {
        entityManager.createQuery("delete from Person p where p.id=:id").setParameter("id", id).executeUpdate();
    }
}
