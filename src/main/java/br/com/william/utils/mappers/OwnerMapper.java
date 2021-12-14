package br.com.william.utils.mappers;

import br.com.william.ower.dtos.OwnerDto;
import br.com.william.ower.model.Owner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface OwnerMapper {

    Owner ownerDtoToOwner(OwnerDto ownerDto);
}
