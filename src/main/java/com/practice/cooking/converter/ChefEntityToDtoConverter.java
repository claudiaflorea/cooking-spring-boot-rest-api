package com.practice.cooking.converter;

import com.practice.cooking.dto.ChefDto;
import com.practice.cooking.model.Chef;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ChefEntityToDtoConverter implements Converter<Chef, ChefDto> {

    @Override
    public ChefDto convert(Chef source) {
        ChefDto chefDto = new ChefDto();
        if (source.getId() != null) {
            chefDto.setId(source.getId());
        }
        chefDto.setId(source.getId());
        chefDto.setName(source.getName());
        return chefDto;
    }
}