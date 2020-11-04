package org.picon.dto;

import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageDto {
    private List<String> imageUrls;
}
