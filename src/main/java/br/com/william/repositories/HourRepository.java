package br.com.william.repositories;

import br.com.william.domain.hour.Hour;
import br.com.william.handlers.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class HourRepository implements PanacheRepository<Hour>{

    public Optional<Hour> findHourByExternalId(String externalId){
        return Optional.ofNullable(find("externalId", UUID.fromString(externalId))
                .singleResultOptional()
                .orElseThrow(() ->
                        new NotFoundException("Day not found")));
    }
}
