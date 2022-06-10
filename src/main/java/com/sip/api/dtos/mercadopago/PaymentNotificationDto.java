package com.sip.api.dtos.mercadopago;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentNotificationDto {
    private String id;
    private boolean live_mode;
    private String type;
    private LocalDateTime date_created;
    private String application_id;
    private String user_id;
    private Integer version;
    private String api_version;
    private String action;
    private MPData data;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    class MPData {
        private String id;
    }
}
