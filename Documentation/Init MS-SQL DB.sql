/*
drop database qcm;
go
*/
drop login adm_qcm;
GO
CREATE LOGIN [adm_qcm] WITH PASSWORD = 'Pa$$w0rd'
GO

Use qcm;
GO

IF NOT EXISTS (SELECT * FROM sys.database_principals WHERE name = N'adm_qcm')
BEGIN
    CREATE USER [adm_qcm] FOR LOGIN [adm_qcm]
    EXEC sp_addrolemember N'db_owner', N'adm_qcm'
END;
GO