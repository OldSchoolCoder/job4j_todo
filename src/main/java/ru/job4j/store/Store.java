package ru.job4j.store;

import ru.job4j.model.Category;
import ru.job4j.model.Item;
import ru.job4j.model.Model;
import ru.job4j.model.User;

import java.util.List;
import java.util.Optional;

public interface Store {

    Model add(Model model);

    Item reverseDone(Integer id);

    List<Item> findAll();

    Optional<User> findByEmail(String email);

    List<Category> getCategories();
}
