-- 승범 

--######################################### 1. SIGNUP
--○ 확인용
DESC HOST;
SELECT * FROM HOST_PROFILE;

DESC MEMBER;
SELECT * FROM MEMBER_PROFILE;

--○ HOST 중복검사
SELECT COUNT(*) AS COUNT
FROM HOST_PROFILE
WHERE HOST_EMAIL='good1@test.com';
--> 한줄
SELECT COUNT(*) AS COUNT FROM HOST_PROFILE WHERE HOST_EMAIL=?
;
SELECT COUNT(*) AS COUNT
FROM HOST_PROFILE
WHERE HOST_NICKNAME='퉁퉁볼';
--> 한줄
SELECT COUNT(*) AS COUNT FROM HOST_PROFILE WHERE HOST_NICKNAME=?
;

--○ MEMBER 중복검사
SELECT COUNT(*) AS COUNT
FROM MEMBER_PROFILE
WHERE MEMBER_EMAIL='test1@test.com';
--> 한줄
SELECT COUNT(*) AS COUNT FROM MEMBER_PROFILE WHERE MEMBER_EMAIL=?
;
SELECT COUNT(*) AS COUNT
FROM MEMBER_PROFILE
WHERE MEMBER_NICKNAME='탱탱볼';
--> 한줄
SELECT COUNT(*) AS COUNT FROM MEMBER_PROFILE WHERE MEMBER_NICKNAME=?
;

--○ HOST 회원가입
-- 회원코드 등록
SELECT H_SEQ.NEXTVAL
FROM DUAL;

INSERT INTO HOST(HOST_CODE, HOST_SIGN_UP_DATE)
VALUES(F_CODE('H', H_SEQ.NEXTVAL), SYSDATE);
--> 한줄
INSERT INTO HOST(HOST_CODE, HOST_SIGN_UP_DATE) VALUES(F_CODE('H', H_SEQ.NEXTVAL), SYSDATE)
;
-- 프로필정보 등록
INSERT INTO HOST_PROFILE(HOST_EMAIL, HOST_CODE
                       , HOST_PW, HOST_NICKNAME
                       , HOST_NAME, HOST_TEL)
VALUES('이메일', F_CODE('H', H_SEQ.CURRVAL), '비번'
      , '닉네임', '이름', '연락처');
--> 한줄
INSERT INTO HOST_PROFILE(HOST_EMAIL, HOST_CODE, HOST_PW, HOST_NICKNAME, HOST_NAME, HOST_TEL) VALUES(?, F_CODE('H', H_SEQ.CURRVAL), ?, ?, ?, ?)
;

-- 프로시저 호출
EXEC CREATE_HOST_ACCOUNT('sb4411@gmail.com', 'JAVA006$', '테스트홍다', '퐁퐁풍', '010-4444-4444');


--○ MEMBER 회원가입
-- 회원코드 등록
SELECT M_SEQ.NEXTVAL
FROM DUAL;

INSERT INTO MEMBER(MEMBER_CODE, MEMBER_SIGN_UP_DATE)
VALUES(F_CODE('M', M_SEQ.NEXTVAL), SYSDATE);
--> 한줄
INSERT INTO MEMBER(MEMBER_CODE, MEMBER_SIGN_UP_DATE) VALUES(F_CODE('M', M_SEQ.NEXTVAL), SYSDATE)
;
-- 프로필정보 등록
INSERT INTO MEMBER_PROFILE(MEMBER_EMAIL, MEMBER_CODE
                         , MEMBER_PW, MEMBER_NICKNAME
                         , MEMBER_NAME, MEMBER_TEL)
VALUES('이메일', F_CODE('M', M_SEQ.CURRVAL), '비번'
      , '닉네임', '이름', '연락처');
--> 한줄
INSERT INTO MEMBER_PROFILE(MEMBER_EMAIL, MEMBER_CODE, MEMBER_PW, MEMBER_NICKNAME, MEMBER_NAME, MEMBER_TEL) VALUES(?, F_CODE('M', M_SEQ.CURRVAL), ?, ?, ?, ?)
;

-- 프로시저 호출
EXEC CREATE_MEMBER_ACCOUNT('sb9999@gmail.com', 'JAVA006$', '테스트다', '홍홍훙', '010-9999-9999');



--######################################### 2.
--######################################### 3.

