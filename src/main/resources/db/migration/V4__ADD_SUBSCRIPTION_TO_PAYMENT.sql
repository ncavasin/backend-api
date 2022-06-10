ALTER TABLE IF EXISTS payment
    ADD COLUMN IF NOT EXISTS subscription_id VARCHAR(255) REFERENCES subscription (id);