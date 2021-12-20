package br.com.william.services;

import br.com.william.Validate;
import br.com.william.handlers.BadRequestExceptionCustom;
import br.com.william.dtos.OwnerDto;
import br.com.william.dtos.OwnerResponse;
import br.com.william.domain.Owner;
import br.com.william.handlers.NotFoundException;
import br.com.william.repositories.OwnerRepository;
import br.com.william.utils.mappers.OwnerMapper;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class OwnerService implements Validate<OwnerDto>{

    OwnerMapper ownerMapper;
    OwnerRepository ownerRepository;
    Validator validator;

    @Inject
    public OwnerService(OwnerMapper ownerMapper,
                        OwnerRepository ownerRepository,
                        Validator validator) {
        this.ownerMapper = ownerMapper;
        this.ownerRepository = ownerRepository;
        this.validator = validator;
    }

    @Transactional
    public String saveOwner(OwnerDto ownerDto) {
        var owner = ownerMapper.owrDtoToOwner(ownerDto);
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
                .orElseThrow(() -> new NotFoundException("Owner not found")));
    }

    @Override
    public void validate(OwnerDto ownerDto) throws BadRequestExceptionCustom {
        Set<ConstraintViolation<OwnerDto>> validation = validator.validate(ownerDto);
        if(!validation.isEmpty()){
            throw new BadRequestExceptionCustom(validation);
        }
    }
}
