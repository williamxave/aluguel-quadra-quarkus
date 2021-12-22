package br.com.william.repositories;

import br.com.william.domain.day.Day;
import br.com.william.handlers.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class DayRepository implements PanacheRepository<Day> {

    public Optional<Day> findDay(String day) {
        return Optional.ofNullable(find("day = :day",
                Parameters.with("day", day))
                .singleResultOptional().orElseThrow(() -> new NotFoundException("Day not found")));
    }

    public Optional<Day> findDayNonException(String day) {
        return find("day = :day",
                Parameters.with("day", day))
                .singleResultOptional();
    }
}
