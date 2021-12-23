package br.com.william.services;

import br.com.william.domain.day.Day;
import br.com.william.domain.day.dtos.DayResponse;
import br.com.william.enums.PossibleHour;
import br.com.william.handlers.BusinessException;
import br.com.william.handlers.NotFoundException;
import br.com.william.repositories.DayRepository;
import br.com.william.repositories.HourRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;

@ApplicationScoped
public class DayService {
    private Logger log = LoggerFactory.getLogger(DayService.class);

    DayRepository dayRepository;
    HourRepository hourRepository;

    @Inject
    public DayService(DayRepository dayRepository,
                      HourRepository hourRepository) {
        this.dayRepository = dayRepository;
        this.hourRepository = hourRepository;
    }

    public DayResponse findDayResponse(String date) {
        var possibleDay = dayRepository.findDay(date);
        checkRentHour(possibleDay.get());
        return possibleDay
                .map(day -> new DayResponse(day)).get();
    }

    public void checkRentHour(Day day) {
        if (day.checkRentHour()) {
            throw new NotFoundException("All hours already rented");
        }
    }

    @Transactional
    public Day createDayToReponse(String date) {
        var day = new Day(date);
        var time =
                PossibleHour.timeGenerator();

        day.addHours(time);
        time.forEach(s -> s.setDay(day));

        dayRepository.persist(day);
        hourRepository.persist(time);

        log.info("Created day successful {}", day.getDay());
        return day;
    }

    @Transactional
    public void updateDay(String externalId) {
        var possibleDay =
                hourRepository.findHourByExternalId(externalId).get();
        if (possibleDay.getChecKRent()) {
            throw new BusinessException("Already rented time");
        }
        possibleDay.updateRentHour();

        log.info("Rented hour successful Day: {}, rent: {}",
                possibleDay.getExternalId(), possibleDay.getChecKRent());

        hourRepository.persist(possibleDay);
    }

    public DayResponse validate(String date) throws BusinessException {
        if (date == null) {
            throw new BusinessException("Enter a valid date");
        }
        if (findDayNonException(date).isPresent()) {
            throw new BusinessException("There is already an equal date registered");
        }
        var day = createDayToReponse(date);
        return new DayResponse(day);
    }

    @Transactional
    public Optional<Day> findDayNonException(String date) {
        return dayRepository.findDayNonException(date);
    }
}