package br.com.william.ower.service;

import br.com.william.ower.dtos.OwnerResponse;
import br.com.william.ower.model.Owner;
import br.com.william.ower.dtos.OwnerDto;
import br.com.william.ower.repository.OwnerRepository;
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
    OwnerRepository ownerRepository;

    @Inject
    public OwnerService(OwnerMapper ownerMapper,
                        OwnerRepository ownerRepository) {
        this.ownerMapper = ownerMapper;
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public String saveOwner(OwnerDto ownerDto) {
        var owner = ownerMapper.ownerDtoToOwner(ownerDto);
        ownerRepository.persist(owner);
        return String.valueOf(owner.getExternalId());
    }

    public OwnerResponse findOwnerResponse(UUID externalId) {
        return findOwner(externalId).map(owner -> new OwnerResponse(owner)).get();
    }

    @Transactional
    public void deleteOwner(UUID externalId) {
         findOwner(externalId).map(owner ->
                 ownerRepository.deleteById(owner.getId()));
    }

    @Transactional
    public void updateOwner(UUID externalId, OwnerDto ownerDto) {
        var owner = findOwner(externalId).get();
        owner.setName(ownerDto.getName());
        owner.setEmail(ownerDto.getEmail());
        owner.setPassword(ownerDto.getPassword());
        ownerRepository.persist(owner);
    }

    public Optional<Owner> findOwner(UUID externalId) {
        return Optional.ofNullable(ownerRepository
                .find("externalId", externalId)
                .firstResultOptional()
                .orElseThrow(() -> new IllegalArgumentException("Owner not found")));
    }

}
