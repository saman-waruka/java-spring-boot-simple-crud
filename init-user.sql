CREATE LOGIN Developer WITH PASSWORD = 'StrongDeveloperPassword123!';
CREATE USER Developer FOR LOGIN Developer;
ALTER ROLE db_owner ADD MEMBER Developer;