CREATE TABLE address (
    id BIGINT NOT NULL AUTO_INCREMENT,
    street VARCHAR(80) NOT NULL,
    city VARCHAR(80) NOT NULL,
    state VARCHAR(80) NOT NULL,
    zipcode VARCHAR(80) NOT NULL,
    country VARCHAR(80) NOT NULL,
    client_id BIGINT NOT NULL,

    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES client(id) ON DELETE CASCADE
);