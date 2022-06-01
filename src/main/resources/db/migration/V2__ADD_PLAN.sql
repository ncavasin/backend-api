CREATE TABLE IF NOT EXISTS plan
(
    id                 VARCHAR(255)     NOT NULL,
    CONSTRAINT pk_plan PRIMARY KEY (id),
    name               VARCHAR(255)     NOT NULL,
    description        VARCHAR(255),
    price              DOUBLE PRECISION NOT NULL,
    activities_limit   INTEGER          NOT NULL,
    creation_timestamp TIMESTAMP        NOT NULL DEFAULT now(),
    update_timestamp   TIMESTAMP        NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS payment
(
    id                 VARCHAR(255)     NOT NULL,
    CONSTRAINT pk_payment PRIMARY KEY (id),
    payment_date       TIMESTAMP        NOT NULL DEFAULT now(),
    amount_paid        DOUBLE PRECISION NOT NULL,
    transaction_id     VARCHAR(255),
    creation_timestamp TIMESTAMP        NOT NULL DEFAULT now(),
    update_timestamp   TIMESTAMP        NOT NULL DEFAULT now()
);


CREATE TABLE IF NOT EXISTS subscription
(
    id                 VARCHAR(255) NOT NULL,
    CONSTRAINT pk_subscription PRIMARY KEY (id),
    description        VARCHAR(255),
    start_date         DATE         NOT NULL,
    end_date           DATE         NOT NULL,
    plan_id            VARCHAR(255) NOT NULL,
    CONSTRAINT fk_subscription_plan FOREIGN KEY (plan_id) REFERENCES plan (id),
    payment_id         VARCHAR(255) NOT NULL,
    CONSTRAINT fk_plan FOREIGN KEY (plan_id) REFERENCES plan (id) ON DELETE CASCADE,
    user_id            VARCHAR(255) NOT NULL,
    CONSTRAINT fk_subscription_user FOREIGN KEY (user_id) REFERENCES user_data (id) ON DELETE CASCADE,
    creation_timestamp TIMESTAMP    NOT NULL DEFAULT now(),
    update_timestamp   TIMESTAMP    NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS plan_subscriptions
(
    plan_id         VARCHAR(255) NOT NULL,
    subscription_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_plan_subscription PRIMARY KEY (plan_id, subscription_id),
    CONSTRAINT fk_plan_subscription_plan FOREIGN KEY (plan_id) REFERENCES plan (id) ON DELETE CASCADE,
    CONSTRAINT fk_plan_subscription_subscription FOREIGN KEY (subscription_id) REFERENCES subscription (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS payment_subscriptions
(
    payment_id      VARCHAR(255) NOT NULL,
    subscription_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_payment_subscription PRIMARY KEY (payment_id, subscription_id),
    CONSTRAINT fk_payment_subscription_payment FOREIGN KEY (payment_id) REFERENCES payment (id) ON DELETE CASCADE,
    CONSTRAINT fk_payment_subscription_subscription FOREIGN KEY (subscription_id) REFERENCES subscription (id) ON DELETE CASCADE
);

