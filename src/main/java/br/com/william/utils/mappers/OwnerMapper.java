package br.com.william.utils.mappers;

import br.com.william.dtos.OwnerDto;
import br.com.william.domain.Owner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface OwnerMapper {

    Owner owrDtoToOwner(OwnerDto ownerDto);

}
