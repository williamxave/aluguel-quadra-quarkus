package br.com.william.services;

import br.com.william.domain.day.Day;
import br.com.william.domain.day.dtos.DayResponse;
import br.com.william.enums.PossibleHour;
import br.com.william.handlers.BusinessException;
import br.com.william.handlers.NotFoundException;
import br.com.william.repositories.DayRepository;
import br.com.william.repositories.HourRepository;
import org.hibernate.validator.constraints.Range;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DayService {

    DayRepository dayRepository;
    HourRepository hourRepository;

    @Inject
    public DayService(DayRepository dayRepository,
                      HourRepository hourRepository) {
        this.dayRepository = dayRepository;
        this.hourRepository = hourRepository;
    }

    @Transactional
    public Optional<Day> finday(String date) {
        return dayRepository.findDay(date);
    }

    public DayResponse findDayResponse(Optional<Day> possibleDay) {
        checkRentHour(possibleDay.get());
        return possibleDay
                .map( day -> new DayResponse(day)).get();
    }

    public void checkRentHour(Day day){
        if(day.checkRentHour()){
            throw new NotFoundException("All hours already rented");
        }
    }

    @Transactional
    public DayResponse createDayToReponse(String date) {
        var day = new Day(date);
        var time =
                PossibleHour.timeGenerator();

        day.addHours(time);
        time.forEach(s -> s.setDay(day));

        dayRepository.persist(day);
        hourRepository.persist(time);
        return new DayResponse(day);
    }

    @Transactional
    public void updateDay(String externalId) {
        var possibleDay =
                hourRepository.find("externalId", UUID.fromString(externalId))
                .singleResultOptional()
                .orElseThrow(() ->
                        new NotFoundException("Day not found"));

        if( possibleDay.getChecKRent() ){
            throw new BusinessException("Already rented time");
        }
        possibleDay.updateRentHour();
        hourRepository.persist(possibleDay);
    }

    public void validate(String date) {
        if(date == null){
            System.out.println("Teste");
        }
    }
}