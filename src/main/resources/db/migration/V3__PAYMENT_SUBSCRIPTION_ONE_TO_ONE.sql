ALTER TABLE IF EXISTS subscription
    DROP COLUMN IF EXISTS payment_id;

DROP TABLE IF EXISTS payment_subscriptions;

ALTER TABLE IF EXISTS payment
    ADD COLUMN IF NOT EXISTS subscription_id VARCHAR(255)
        REFERENCES subscription (id);