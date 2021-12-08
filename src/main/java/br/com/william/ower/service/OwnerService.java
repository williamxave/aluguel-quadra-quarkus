package br.com.william.ower.service;

import br.com.william.ower.model.Owner;
import br.com.william.ower.dtos.OwnerDto;
import br.com.william.ower.repository.OwnerRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
public class OwnerService {

    OwnerRepository ownerRepository;

    @Inject
    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Transactional
    public String saveOwner(OwnerDto ownerDto){
        var owner = new Owner();
        owner.setEmail(ownerDto.getEmail());
        owner.setName(ownerDto.getName());
        owner.setPassword(ownerDto.getPassword());

        ownerRepository.persist(owner);
        return String.valueOf(owner.getExternalId());
    }
}
