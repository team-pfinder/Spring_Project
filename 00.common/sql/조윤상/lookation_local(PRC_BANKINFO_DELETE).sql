--○ 계좌 삭제 (프로시저)
CREATE OR REPLACE PROCEDURE PRC_BANKINFO_DELETE
( V_IDENTIFY    IN VARCHAR2
, BANK_INFO     IN VARCHAR2
)
IS
    -- 선언부
    
BEGIN
    -- 실행부
    
    IF(V_IDENTIFY = 'member')
        THEN    
            DELETE 
            FROM MEMBER_EXCHANGE_BANK_INFO
            WHERE MEMBER_BANK_NUMBER = BANK_INFO;
        
            DELETE 
            FROM LOAD_REG_BANK_INFO
            WHERE MEMBER_BANK_NUMBER = BANK_INFO;
            
            DELETE 
            FROM MEMBER_BANK_INFO
            WHERE MEMBER_BANK_NUMBER = BANK_INFO;
            
            -- 커밋
            COMMIT;
            
    ELSIF(V_IDENTIFY = 'host')
        THEN
            DELETE
            FROM HOST_EXCHANGE_BANK_INFO
            WHERE HOST_BANK_NUMBER = BANK_INFO;
            
            DELETE
            FROM HOST_BANK_INFO
            WHERE HOST_BANK_NUMBER = BANK_INFO;
            
            -- 커밋
            COMMIT;
    ELSE    
        -- 예외 처리
        ROLLBACK;
   END IF;     

    
END;