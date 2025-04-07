package com.grey.cagnotte.payload.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequest {
    private String label;
    private String icone;
    private boolean allowConcern;
    private boolean allowMessage;
    private boolean allowMedia;
    private boolean allowLocation;
    private boolean allowUrl;

}
