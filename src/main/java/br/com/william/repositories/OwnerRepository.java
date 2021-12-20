package br.com.william.repositories;

import br.com.william.domain.Owner;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OwnerRepository implements PanacheRepository<Owner>{
}
