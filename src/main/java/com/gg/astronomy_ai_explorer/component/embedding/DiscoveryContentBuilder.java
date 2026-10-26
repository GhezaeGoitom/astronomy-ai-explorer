package com.gg.astronomy_ai_explorer.component.embedding;


import com.gg.astronomy_ai_explorer.util.EntityType;

public interface DiscoveryContentBuilder {
    String buildContent(EntityType entityType, Object entity);
}