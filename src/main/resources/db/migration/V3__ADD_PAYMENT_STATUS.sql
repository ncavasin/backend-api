CREATE TABLE IF NOT EXISTS payment_status
(
    id                 VARCHAR(255) NOT NULL,
    CONSTRAINT pk_payment_status PRIMARY KEY (id),
    payment_id         VARCHAR(255) NOT NULL,
    CONSTRAINT fk_payment_status_payment FOREIGN KEY (payment_id) REFERENCES payment (id),
    payment_status     VARCHAR(255) NOT NULL,
    init_timestamp     TIMESTAMP             DEFAULT now(),
    end_timestamp      TIMESTAMP             DEFAULT now(),
    is_current         BOOLEAN,
    creation_timestamp TIMESTAMP    NOT NULL DEFAULT now(),
    update_timestamp   TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS payment_payment_status
(
    payment_id        VARCHAR(255) NOT NULL,
    payment_status_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_payment_payment_status PRIMARY KEY (payment_id, payment_status_id),
    CONSTRAINT fk_payment_payment_status_payment FOREIGN KEY (payment_id) REFERENCES payment (id),
    CONSTRAINT fk_payment_payment_status_payment_status FOREIGN KEY (payment_status_id) REFERENCES payment_status (id)
);