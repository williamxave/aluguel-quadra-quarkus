package br.com.william.ower.repository;

import br.com.william.ower.model.Owner;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OwnerRepository implements PanacheRepository<Owner> {
}
