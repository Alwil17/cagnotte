package com.grey.cagnotte.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private long id;
    private String label;
    private String slug;
    private String icon;
    private String description;
}
