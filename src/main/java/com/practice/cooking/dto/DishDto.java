package com.practice.cooking.dto;

import javax.validation.constraints.NotEmpty;

import com.practice.cooking.model.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class DishDto {

    @NotEmpty
    private Long   id;
    @NotEmpty
    private String name;
    private Recipe recipe;
}
