package br.com.william.ower.repository;

import br.com.william.ower.model.Owner;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.NamedQuery;
import javax.ws.rs.QueryParam;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NamedQuery(
        name = "findOwnerByExternalId",
        query = "SELECT u FROM Owner u WHERE u.externalId = :externalId")
public class OwnerRepository implements PanacheRepository<Owner> {

    public Optional<Owner> findOwnerByExternalId(@QueryParam("externalId")UUID externalId) {
     return find("externalId = :externalId", Parameters.with("externalId", externalId)).firstResultOptional();
    }
}
