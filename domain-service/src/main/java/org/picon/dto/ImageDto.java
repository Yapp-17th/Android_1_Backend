package org.picon.dto;

import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageDto {
    private List<String> imageUrls;
}
