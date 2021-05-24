package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.job4j.model.Item;

import java.util.List;
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
    public Item add(Item item) {
        this.wrapper(session -> session.save(item));
        return item;
    }

    @Override
    public Item update(Integer id) {
        return this.wrapper(session -> {
            Item item = session.get(Item.class, id);
            item.setDone(!item.getDone());
            return item;
        });
    }

    @Override
    public List<Item> findAll() {
        return this.wrapper(session -> session.createQuery("from Item").list());
    }
}

