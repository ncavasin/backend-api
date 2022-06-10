ALTER TABLE IF EXISTS payment
    ADD COLUMN payment_status VARCHAR(255);

DROP TABLE IF EXISTS payment_subscriptions;
