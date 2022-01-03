package br.com.william.services;

import br.com.william.domain.day.Day;
import br.com.william.domain.day.dtos.DayResponse;
import br.com.william.enums.PossibleHour;
import br.com.william.handlers.BusinessException;
import br.com.william.handlers.NotFoundException;
import br.com.william.repositories.DayRepository;
import br.com.william.repositories.HourRepository;
import br.com.william.repositories.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class DayService {
    private Logger log = LoggerFactory.getLogger(DayService.class);

    DayRepository dayRepository;
    HourRepository hourRepository;
    OwnerRepository ownerRepository;

    @Inject
    public DayService(DayRepository dayRepository,
                      HourRepository hourRepository,
                      OwnerRepository ownerRepository) {
        this.dayRepository = dayRepository;
        this.hourRepository = hourRepository;
        this.ownerRepository = ownerRepository;
    }

    public DayResponse findDayResponse(String date) {
        var possibleDay = dayRepository.findDay(date);
        checkRentHour(possibleDay.get());
        return possibleDay
                .map(day -> new DayResponse(day)).get();
    }

    private void checkRentHour(Day day) {
        if (day.checkRentHour()) {
            throw new NotFoundException("All hours already rented");
        }
    }

    @Transactional
    private Day createDayToReponse(String date) {
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
    public void updateDay(String externalId, String externalIdOwner) {
        var possibleDay =
                hourRepository.findHourByExternalId(externalId).get();
        if (possibleDay.getChecKRent()) {
            throw new BusinessException("Already rented time");
        }

        possibleDay.updateRentHour();

        var owner =
                ownerRepository.findOwnerByExternalId(UUID.fromString(externalIdOwner));

        owner.get().addHours(possibleDay);

        hourRepository.persist(possibleDay);
        ownerRepository.persist(owner.get());

        log.info("Rented hour successful Day: {}, Owner: {}",
                possibleDay.getExternalId(), owner.get().getEmail());
    }

    @Transactional
    public DayResponse validate(String date) throws BusinessException {
        if (date == null || date.isEmpty()) {
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