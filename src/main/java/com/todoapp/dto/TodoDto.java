package com.todoapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        description = "Todo dto model information"
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    private Long id;

        @Schema(
                description = "todo title"
        )
        @NotEmpty(message = "title cant be empty")
        private String title;
    @Schema(
            description = "todo description"
    )   @NotEmpty(message = "description cant be empty")
        private String description;
    @Schema(
            description = "completed or uncompleted"
    )
        private boolean completed;
    }
