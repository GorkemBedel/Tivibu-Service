package com.Test.Tivibu.mapper;

import com.Test.Tivibu.dto.DeviceDto;
import com.Test.Tivibu.model.Device;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface DeviceMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDeviceFromDto(DeviceDto deviceDto, @MappingTarget Device device);
}
