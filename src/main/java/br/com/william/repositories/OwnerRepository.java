package br.com.william.repositories;

import br.com.william.domain.owner.Owner;
import br.com.william.handlers.NotFoundException;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OwnerRepository implements PanacheRepository<Owner>{

    public Optional<Owner> findOwnerByExternalId(UUID externalId){
      return Optional.ofNullable(find("externalId", externalId)
              .firstResultOptional()
              .orElseThrow(() -> new NotFoundException("Owner not found")));
    }
}
