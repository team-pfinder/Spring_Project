SELECT USER
FROM DUAL;

SELECT *
FROM DBA_USERS;

-- ���� ����
CREATE USER LOOKATION IDENTIFIED BY java006$;
--==>> User LOOKATION��(��) �����Ǿ����ϴ�.

-- ���� DBA ���� �ο�
GRANT CONNECT, RESOURCE, DBA TO LOOKATION;
--==>> Grant��(��) �����߽��ϴ�.

-- ���ν��� ���� ���� �ο�
GRANT CREATE PROCEDURE TO LOOKATION;
--==>> Grant��(��) �����߽��ϴ�.

-- �� ���� ���� �ο�
GRANT CREATE VIEW TO LOOKATION;
--==>> Grant��(��) �����߽��ϴ�.

-- Ʈ���� ���� ���� �ο�
GRANT CREATE TRIGGER TO LOOKATION;
--==>> Grant��(��) �����߽��ϴ�.

-- ���̺� �����̽� ���� �ο�
GRANT CREATE TABLESPACE TO LOOKATION;
--==>> Grant��(��) �����߽��ϴ�.

/*
-- ��ȣȭ ��Ű�� ��ġ
@E:\oracleexe\app\oracle\product\11.2.0\server\rdbms\admin\dbmsobtk.sql
@E:\oracleexe\app\oracle\product\11.2.0\server\rdbms\admin\prvtobtk.plb

-- ��ȣȭ ��Ű�� ��� ���� �ο�
GRANT EXECUTE ON DBMS_OBFUSCATION_TOOLKIT TO LOOKATION;
--==>> Grant��(��) �����߽��ϴ�.
*/
