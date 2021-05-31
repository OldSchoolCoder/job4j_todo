package ru.job4j.store;

import ru.job4j.model.Item;
import ru.job4j.model.Model;
import ru.job4j.model.User;

import java.util.List;

public interface Store {

    Model add(Model model);

    Item reverseDone(Integer id);

    List<Item> findAll();

    List<User> findByEmail(String email);
}
