package com.gg.astronomy_ai_explorer.dto.embedding;

import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SemanticSearchResultDto {

    private String entityType;

    private Long entityId;

    private String content;
}