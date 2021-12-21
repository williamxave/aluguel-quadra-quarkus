package br.com.william.repositories;

import br.com.william.domain.day.Day;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;
import org.jboss.resteasy.annotations.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NamedQuery;
import java.util.Optional;

@ApplicationScoped
public class DayRepository implements PanacheRepository<Day>{

    public Optional<Day> findDay(String day){
        return find("day = :day",
                Parameters.with("day", day))
                .singleResultOptional();
    }


}
