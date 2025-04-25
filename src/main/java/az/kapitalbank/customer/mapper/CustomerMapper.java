package az.kapitalbank.customer.mapper;

import az.kapitalbank.customer.dao.entity.CustomerEntity;
import az.kapitalbank.customer.model.dto.CustomerCreateRequestDto;
import az.kapitalbank.customer.model.dto.CustomerCreateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, imports = java.math.BigDecimal.class)
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    @Mapping(target = "balance", expression = "java(BigDecimal.valueOf(100))")
    CustomerEntity toCustomerEntity(CustomerCreateRequestDto source);

    @Mapping(target = "customerId", source = "id")
    CustomerCreateResponseDto toCustomerCreateResponseDto(CustomerEntity source);
}
