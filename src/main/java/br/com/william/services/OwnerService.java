package br.com.william.services;

import br.com.william.domain.owner.Owner;
import br.com.william.domain.owner.dtos.OwnerDto;
import br.com.william.domain.owner.dtos.OwnerResponse;
import br.com.william.handlers.BadRequestExceptionCustom;
import br.com.william.repositories.OwnerRepository;
import br.com.william.utils.PasswordEncrypt;
import br.com.william.utils.Validate;
import br.com.william.utils.mappers.OwnerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class OwnerService implements Validate<OwnerDto> {
    private final Logger log = LoggerFactory.getLogger(OwnerService.class);

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
        owner.encryptPassword(ownerDto.getPassword());
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

    private Optional<Owner> findOwner(UUID externalId) {
        return ownerRepository.findOwnerByExternalId(externalId);
    }

    public void login(OwnerDto ownerDto) {
        var owner =
                ownerRepository.findOwnerByEmail(ownerDto.getEmail()).get();
        try {
            verifyPassword(owner, ownerDto);
        }catch (IllegalArgumentException e){
            log.error("Invalid password owner: {} ", owner.getExternalId());
            throw e;
        }
    }

    @Transactional
    private void verifyPassword(Owner owner, OwnerDto ownerDto) {
        if (!owner.getPassword().equals(PasswordEncrypt.encryptPassword(ownerDto.getPassword()))) {
            throw new IllegalArgumentException("Invalid email or password");
        }
        owner.setLastLogin();
        ownerRepository.persist(owner);
    }

    @Override
    public void validate(OwnerDto ownerDto) throws BadRequestExceptionCustom {
        Set<ConstraintViolation<OwnerDto>> validation = validator.validate(ownerDto);
        if (!validation.isEmpty()) {
            throw new BadRequestExceptionCustom(validation);
        }
    }
}
