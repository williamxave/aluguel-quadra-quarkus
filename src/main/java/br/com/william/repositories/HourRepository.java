package br.com.william.repositories;

import br.com.william.domain.hour.Hour;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class HourRepository implements PanacheRepository<Hour>{
}
