-- create TYPE user_status AS ENUM ('ACTIVE', 'INACTIVE', 'DELETED', 'OVERDUE');

CREATE TABLE IF NOT EXISTS user_data
(
    id                 VARCHAR(255)                    NOT NULL
        CONSTRAINT pk_user_data PRIMARY KEY,
    dni                numeric                         NOT NULL UNIQUE,
    password           VARCHAR(255)                    NOT NULL,
    email              VARCHAR(255)                    NOT NULL UNIQUE,
    phone              BIGINT,
    first_name         VARCHAR(255),
    last_name          VARCHAR(255),
    birth_date         DATE,
    status             VARCHAR(255) DEFAULT 'INACTIVE' NOT NULL,
    creation_timestamp timestamp    DEFAULT NOW(),
    update_timestamp   timestamp    DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS mail_token
(
    id           VARCHAR(255) NOT NULL
        CONSTRAINT PK_mail_token PRIMARY KEY,
    confirmed_at TIMESTAMP,
    created_at   TIMESTAMP,
    expires_at   TIMESTAMP,
    token        VARCHAR(255) NOT NULL,
    user_id      VARCHAR(255) NOT NULL
        CONSTRAINT fk_mail_token_user_data
            REFERENCES user_data (id)
);

CREATE TABLE IF NOT EXISTS resource
(
    id                 VARCHAR(255)            NOT NULL
        CONSTRAINT pk_resources PRIMARY KEY,
    name               VARCHAR(255)            NOT NULL,
    url                VARCHAR(255),
    creation_timestamp TIMESTAMP DEFAULT NOW() NOT NULL,
    update_timestamp   TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS role
(
    id                 VARCHAR(255) NOT NULL
        CONSTRAINT PK_roles PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    creation_timestamp TIMESTAMP DEFAULT NOW(),
    update_timestamp   TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS role_resource
(
    role_id     VARCHAR(255) NOT NULL REFERENCES role (id),
    resource_id VARCHAR(255) NOT NULL REFERENCES resource (id),
    CONSTRAINT pk_roles_resources PRIMARY KEY (role_id, resource_id),
    CONSTRAINT fk_roles
        FOREIGN KEY (role_id) REFERENCES role ON DELETE CASCADE,
    CONSTRAINT fk_resources
        FOREIGN KEY (resource_id) REFERENCES resource ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS user_data_role
(
    user_id VARCHAR(255) NOT NULL,
    role_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_user_data_role PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_data
        FOREIGN KEY (user_id) REFERENCES user_data ON DELETE CASCADE,
    CONSTRAINT fk_role
        FOREIGN KEY (role_id) REFERENCES role ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS timeslot
(
    id                 VARCHAR(255) NOT NULL
        CONSTRAINT pk_timeslot PRIMARY KEY,
    start_time         TIME         NOT NULL,
    end_time           TIME         NOT NULL,
    day_of_week        INTEGER      NOT NULL,
    creation_timestamp TIMESTAMP DEFAULT NOW(),
    update_timestamp   TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS activity
(
    id                 VARCHAR(255) NOT NULL
        CONSTRAINT pk_activity PRIMARY KEY,
    name               VARCHAR(255) NOT NULL,
    base_price         DOUBLE PRECISION,
    user_id            VARCHAR(255) NOT NULL,
    CONSTRAINT fk_activity_user_data
        FOREIGN KEY (user_id) REFERENCES user_data (id) ON DELETE CASCADE,
    attendees_limit    INTEGER      NOT NULL,
    creation_timestamp TIMESTAMP DEFAULT NOW(),
    update_timestamp   TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS available_class
(
    id                 VARCHAR(255) NOT NULL
        CONSTRAINT pk_available_class PRIMARY KEY,
    activity_id        VARCHAR(255) NOT NULL,
    timeslot_id        VARCHAR(255) NOT NULL,
    creation_timestamp TIMESTAMP DEFAULT NOW(),
    update_timestamp   TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_activity
        FOREIGN KEY (activity_id) REFERENCES activity ON DELETE CASCADE,
    CONSTRAINT fk_timeslot
        FOREIGN KEY (timeslot_id) REFERENCES timeslot ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservation
(
    id                 VARCHAR(255) NOT NULL
        CONSTRAINT pk_reservation PRIMARY KEY,
    available_class_id VARCHAR(255) NOT NULL UNIQUE,
    creation_timestamp TIMESTAMP DEFAULT NOW(),
    update_timestamp   TIMESTAMP DEFAULT NOW(),
    CONSTRAINT fk_available_class
        FOREIGN KEY (available_class_id) REFERENCES available_class ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS reservation_user_data
(
    user_id        VARCHAR(255) NOT NULL,
    reservation_id VARCHAR(255) NOT NULL,
    CONSTRAINT fk_user_data
        FOREIGN KEY (user_id) REFERENCES user_data ON DELETE CASCADE,
    CONSTRAINT fk_reservation
        FOREIGN KEY (reservation_id) REFERENCES reservation ON DELETE CASCADE,
    CONSTRAINT pk_reservation_user_data PRIMARY KEY (user_id, reservation_id)
)



