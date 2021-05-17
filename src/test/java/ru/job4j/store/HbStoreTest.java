package ru.job4j.store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.sql.Timestamp;
import java.util.List;

import static org.junit.Assert.*;

public class HbStoreTest {

    private Item item;
    private HbStore store;
    private SessionFactory sessionFactory;
    private StandardServiceRegistry registry;

    @Before
    public void setUp() throws Exception {
        this.registry = new StandardServiceRegistryBuilder().configure().build();
        this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        this.store = new HbStore();
        this.item = new Item("TestDescription", new Timestamp(436746464L), false);
    }

    @After
    public void tearDown() {
        StandardServiceRegistryBuilder.destroy(registry);
        ;
    }

    @Test
    public void add() {
        store.add(item);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Integer id = item.getId();
        Item itemFromStore = session.get(Item.class, id);
        session.getTransaction().commit();
        session.close();
        Assert.assertEquals(item, itemFromStore);
    }
}