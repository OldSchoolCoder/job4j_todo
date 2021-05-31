package ru.job4j.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HbStoreTest {

    private Item item;
    private List<Item> items = new ArrayList<>();
    private User user = new User();
    private HbStore store;
    private SessionFactory sessionFactory;
    private StandardServiceRegistry registry;

    @Before
    public void setUp() throws Exception {
        this.registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        this.store = new HbStore();
        this.user = new User("Donald2", "mail2", "pass2");
        this.item = new Item("TestDescription2", new Timestamp(System.currentTimeMillis()), false, user);
    }

    @After
    public void tearDown() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T wrapper(final Function<Session, T> command) {
        Session session = sessionFactory.openSession();
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

    @Test
    public void testAdd() {
        store.add(item);
        Item savedItem = this.wrapper(session -> session.get(Item.class, item.getId()));
        Assert.assertEquals(item, savedItem);
    }

    @Test
    public void testUpdate() {
        store.add(item);
        Boolean OldDone = this.item.getDone();
        Integer id = item.getId();
        Item savedItem = store.reverseDone(id);
        Boolean newDone = savedItem.getDone();
        Assert.assertNotEquals(OldDone, newDone);
    }
}