package com.jimmy.common.data;


import com.jimmy.common.bean.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.MONDAY;


// TODO переделать Schedule так что б он был похож на Ивенты + доп функционал который есть в приложухе


public class ScheduleDaoNew {
    // TODO 1 Это новый класс с которого мы будем брать наши Ивенты. Берем их с общих системных календарей


    public List<Schedule> getScheduleByDate(int year, int month, int day){
        List<Schedule> schedules = new ArrayList<>();
        // TODO 2 Принимает день месяц и год как параметры. Отправляет запрос в системный календарь и получает объект cursor
        // TODO 3 Проходится по курсору и создает объекты Schedule с помощью функции makeSchedule(params)
        // TODO 4 Возвращается schedules
        return schedules;
    }

    public Schedule makeSchedule(String... params){
        Schedule schedule = new Schedule();
        // TODO 5 принимает параметры для инициализации и возвращает объект Schedule
        return schedule;
    }

}
// TODO все ок 234 давай ты сделаешь и я потом просмотрю 5 я сделаю и ты потом просмотришь