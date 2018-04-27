package ru.javawebinar.topjava.web.meal;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/ajax/meals/")
public class MealsAjaxController  extends AbstractMealController{

    @RequestMapping("{id}")
    public void delete(@PathVariable("id") int id){
        super.delete(id);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealWithExceed> getAll(){
       return super.getAll();
    }

    @PostMapping
    public void createOrUpdate(@RequestParam("dateTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDateTime localDateTime,
                               @RequestParam("description") String description,
                               @RequestParam("calories")int calories) {
        super.create(new Meal(localDateTime,description,calories));
    }

}
