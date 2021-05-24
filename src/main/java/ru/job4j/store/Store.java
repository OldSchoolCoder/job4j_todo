package ru.job4j.store;

import ru.job4j.model.Item;

import java.util.List;

public interface Store {

    Item add(Item item);

    Item update(Integer id);

    List<Item> findAll();
}
