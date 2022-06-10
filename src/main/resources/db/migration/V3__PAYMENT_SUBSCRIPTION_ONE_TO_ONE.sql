ALTER TABLE IF EXISTS payment
    ADD COLUMN payment_status VARCHAR(255);

ALTER TABLE IF EXISTS payment
    ADD COLUMN IF NOT EXISTS subscription_id VARCHAR(255) REFERENCES subscription (id);

ALTER TABLE IF EXISTS subscription
    ADD COLUMN IF NOT EXISTS payment_subscription_id VARCHAR(255) REFERENCES subscription (id);

DROP TABLE IF EXISTS payment_subscriptions;
