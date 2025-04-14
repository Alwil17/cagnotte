package com.grey.cagnotte.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class CategoryRequest {
    private String label;
    private String description;
    private String icon;
}
