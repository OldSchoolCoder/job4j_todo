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
import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class HbStoreTest {

    private Item item;
    private Item item2;
    private List<Category> categories = new ArrayList<>();
    private User user = new User();
    private User user2 = new User();
    private HbStore store;
    private SessionFactory sessionFactory;
    private StandardServiceRegistry registry;
    private Category category;

    @Before
    public void setUp() throws Exception {
        this.registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        this.store = new HbStore();
        this.user = new User("Donald21", "mail2134", "pass21");
        this.user2 = new User("Donald212", "mail21235", "pass212");
        this.item = new Item("TestDescription21", new Timestamp(System.currentTimeMillis()), false, user, categories);
        this.item2 = new Item("TestDescription212", new Timestamp(System.currentTimeMillis()), false, user2, categories);
        this.category = new Category("Hard");
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
    public void testAddCategory() {
        store.add(category);
        Category savedCategory = this.wrapper(session -> session.get(Category.class, category.getId()));
        Assert.assertEquals(category, savedCategory);
    }

    @Test
    public void testUpdate() {
        store.add(item2);
        Boolean OldDone = this.item2.getDone();
        Integer id = item2.getId();
        Item savedItem = store.reverseDone(id);
        Boolean newDone = savedItem.getDone();
        Assert.assertNotEquals(OldDone, newDone);
    }
}