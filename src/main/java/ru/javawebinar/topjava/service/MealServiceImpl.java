package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.mock.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    @Autowired
    private MealRepository repository;

    @Override
    public Meal save(Meal meal, int userid) {
         log.info("save",meal);
        return repository.save(meal, userid);
    }

    @Override
    public void delete(int id, int userid) throws NotFoundException {
        log.info("delete",id);
        repository.delete(id, userid);

    }

    @Override
    public Meal get(int id, int userid) throws NotFoundException {
        log.info("get",id);
        return repository.get(id, userid);
    }

    @Override
    public void update(Meal meal, int userid) {
        log.info("update",meal);
        repository.save(meal,userid );

    }

    @Override
    public List<Meal> getAll(int userid) {
        log.info("getAll",userid);
        return repository.getAll(userid);
    }

    @Override
    public List<Meal> getAllByDateTime(int userid, LocalDateTime start, LocalDateTime stop) {
        log.info("getAllByDateTime",userid,start,stop);
        return repository.getAllByDateTime(userid, start,stop);
    }
}