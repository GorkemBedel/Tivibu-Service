package com.Test.Tivibu.mapper;

import com.Test.Tivibu.dto.DeviceDto;
import com.Test.Tivibu.dto.TestDto;
import com.Test.Tivibu.model.Test;
import com.Test.Tivibu.model.device.Device;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface TestMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    default void updateTestFromDto(TestDto testDto, @MappingTarget Test test){
        if (testDto.subTests().isPresent()) {
            test.setSubTests(testDto.subTests().get());
        }
    }
}
