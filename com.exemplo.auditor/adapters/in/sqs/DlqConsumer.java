package com.exemplo.auditor.adapters.in.sqs;

import com.exemplo.auditor.adapters.in.sqs.dto.SqsMessageDto;
import com.exemplo.auditor.ports.in.ProcessDeadLetterUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Component
public class DlqConsumer {

    private final ProcessDeadLetterUseCase useCase;
    private final ObjectMapper objectMapper;

    public DlqConsumer(ProcessDeadLetterUseCase useCase, ObjectMapper objectMapper) {
        this.useCase = useCase;
        this.objectMapper = objectMapper;
    }

    @SqsListener("${cloud.aws.sqs.dlq-name}")
    public void consume(String rawJson) {
        try {
            System.out.println("====== MENSAGEM RECEBIDA NA DLQ ======");
            System.out.println(rawJson);

            SqsMessageDto dto = objectMapper.readValue(rawJson, SqsMessageDto.class);

            useCase.execute(dto.toDomain(), rawJson);

            System.out.println("Mensagem processada e persistida com sucesso.");
        } catch (Exception e) {
            System.err.println("Erro ao processar mensagem da DLQ: " + e.getMessage());
            throw new RuntimeException("Falha no processamento do Auditor", e);
        }
    }
}