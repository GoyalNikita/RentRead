package com.crio.rentRead.exchanges;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateBookRequest {

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @NotEmpty
    private String genre;

    @NotEmpty
    private String availabilityStatus;
}
