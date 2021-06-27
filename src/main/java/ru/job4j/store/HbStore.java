package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.Model;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class HbStore implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    private <T> T wrapper(final Function<Session, T> command) {
        Session session = sf.openSession();
        session.beginTransaction();
        try {
            T result = command.apply(session);
            session.getTransaction().commit();
            return result;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Override
    public Model add(Model model) {
        this.wrapper(session -> session.save(model));
        return model;
    }

    @Override
    public Item reverseDone(Integer id) {
        return this.wrapper(session -> {
            Item item = session.get(Item.class, id);
            item.setDone(!item.getDone());
            return item;
        });
    }

    @Override
    public List<Item> findAll() {
        return this.wrapper(session -> session.createQuery("select distinct i from Item i join fetch i.categories").list());
    }

    @Override
    public Optional<User> findByEmail(String email) {
        User user = (User) this.wrapper(session -> {
            final Query query = session.createQuery("from User where email=:email");
            query.setParameter("email", email);
            return query.uniqueResult();
        });
        return Optional.ofNullable(user);
    }

    @Override
    public List<Category> getCategories() {
        return this.wrapper(session -> session.createQuery("from Category ").list());
    }

    public Category getCategoryById(Integer id) {
        return this.wrapper(session -> session.find(Category.class, id));
    }
}

