CREATE DATABASE SpringSecurityLogin;
GO
USE SpringSecurityLogin;
GO

INSERT INTO roles (name)
SELECT 'ROLE_USER'
WHERE NOT EXISTS (
    SELECT 1
    FROM roles
    WHERE name = 'ROLE_USER'
);

INSERT INTO roles (name)
SELECT 'ROLE_ADMIN'
WHERE NOT EXISTS (
    SELECT 1
    FROM roles
    WHERE name = 'ROLE_ADMIN'
);


SELECT *
FROM products;

SELECT *
FROM users;