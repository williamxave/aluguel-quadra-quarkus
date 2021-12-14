package br.com.william.ower.service;

import br.com.william.ower.dtos.OwnerResponse;
import br.com.william.ower.model.Owner;
import br.com.william.ower.dtos.OwnerDto;
import br.com.william.utils.mappers.OwnerMapper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class OwnerService {

    OwnerMapper ownerMapper;

    @Inject
    public OwnerService(OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

    @Transactional
    public String saveOwner(OwnerDto ownerDto) {
        var owner = ownerMapper.ownerDtoToOwner(ownerDto);
        Owner.persist(owner);
        return String.valueOf(owner.getExternalId());
    }

    @Transactional
    public OwnerResponse findOwnerResponse(UUID externalId) {
        var possibleOwner =
                findOwner(externalId).map(owner -> new OwnerResponse(owner)).get();
        return possibleOwner;
    }

    public Optional<Owner> findOwner(UUID externalId){
       return  Optional.ofNullable((Owner) Owner.find("externalId", externalId)
               .firstResultOptional()
               .orElseThrow(() -> new IllegalArgumentException("Owner not found")));
    }
}
