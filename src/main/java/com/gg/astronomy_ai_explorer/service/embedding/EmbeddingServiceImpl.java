package com.gg.astronomy_ai_explorer.service.embedding;

import com.gg.astronomy_ai_explorer.repository.embedding.DiscoveryEmbeddingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmbeddingServiceImpl implements EmbeddingService {

    private final EmbeddingModel embeddingModel;
    private DiscoveryEmbeddingRepository embeddingRepository;

    @Override
    public float[] generateEmbedding(String content) {
        return embeddingModel.embed(content);
    }


}