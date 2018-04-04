package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.util.List;


@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Query("SELECT m FROM Meal m WHERE m.user.id=:userid ORDER BY m.dateTime DESC")
    List<Meal> getAll(@Param("userid") int userid);

    @Query("SELECT m FROM Meal  m WHERE m.id=:id AND m.user.id=:userid")
    Meal getByIdAndUserId(@Param("id") int id, @Param("userid") int userid);

    @Override
    @Transactional
    Meal save(Meal meal);

    @Query("SELECT m FROM Meal m " +
            "WHERE m.user.id=:userId AND m.dateTime BETWEEN :startDate AND :endDate ORDER BY m.dateTime DESC")
    List<Meal> getBetweenDates(@Param("startDate") LocalDateTime start, @Param("endDate") LocalDateTime end, @Param("userId") int id);
    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:userid")
    int delete(@Param("id") int id, @Param("userid") int userid);
}
