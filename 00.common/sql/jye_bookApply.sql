/*
* bookApply 5
●
1. 공간테이블에서 예약할 공간의 정보를 조회하는 쿼리문
   1-1. 기본설명 : 공간설명(공간한줄소개), 공간유형, 예약인원(최소,최대)
   1-2. 예약정보 : 예약날짜(적용패키지.적용일자), 예약인원(전페이지로부터 받아옴)
   1-3. 예약자정보 : (디폴트값으로 하는경우)회원정보(이름, 이메일, 연락처)
   1-4. 호스트정보 : 공간상호명, 대표자명, 소재지, 사업자번호, 연락처(이메일, 휴대폰, 대표전화)
   1-5. 주의사항 : 주의사항.주의사항 내용
♥
1. 예약자정보는 직접 입력도 가능하지만 체크 버튼을 통해 현재 로그인한 회원의 정보를 디폴트값으로 받아올 수 있도록 
2. 필수입력사항 미입력, 서비스 동의를 하지 않으면 결제 불가능
*/


--○ 예약자정보
SELECT MEMBER_NAME, MEMBER_EMAIL, MEMBER_TEL, MEMBER_CODE, 
FROM MEMBER_PROFILE;

--○ 사업자정보
SELECT B.BIZ_NAME, B.BIZ_CEO, B.BIZ_CEO_TYPE,
B.BIZ_MAIN_TYPE, B.BIZ_SUB_TYPE, B.BIZ_LICENSE_NUMBER
, LC.LOC_EMAIL, LC.LOC_TEL, LC.LOC_MAIN_TEL
, B.BIZ_ADDR, B.LOC_CODE
FROM BIZ_INFO B
    JOIN LOC_CONTACT LC
    ON B.LOC_CODE = LC.LOC_CODE
WHERE B.LOC_CODE='L000001';
--
SELECT LOC_CODE, BIZ_NAME, BIZ_CEO, BIZ_LICENSE_NUMBER
, BIZ_ADDR, LOC_EMAIL, LOC_TEL, LOC_MAIN_TEL 
FROM VIEW_BIZ_CONTACT
WHERE LOC_CODE = 'L000001';

--○ 선택한 가격만큼 마일리지 남아있는지 검증 


--○ 패키지 금액 조회

--○ 마일리지 있으면 예약 내역테이블 insert
INSERT INTO BOOK_LIST (BOOK_CODE, MEMBER_CODE, APPLY_PACKAGE_CODE
, BOOK_PEOPLE, BOOK_DATE, BOOK_REQ)
VALUES(F_CODE('BC', BC_SEQ.NEXTVAL), 'M000001', 'AP000001', 3, SYSDATE, '잘해주세요');

--○ 방금 한 예약번호 가져옴
SELECT BOOK_CODE
FROM 
(SELECT BOOK_CODE FROM BOOK_LIST WHERE MEMBER_CODE='M000001' ORDER BY BOOK_CODE DESC)
WHERE ROWNUM=1;

--○ 실예약자 테이블 insert
INSERT INTO ACTUAL_BOOKER (ACTUAL_BOOKER_CODE, BOOK_CODE, ACTUAL_BOOKER
, ACTUAL_BOOKER_TEL)
VALUES(F_CODE('AB', AB_SEQ.NEXTVAL), 'BC000006', '진짜영은', '010-3690-7828');


--○ 예약결제내역 테이블 insert
INSERT INTO BOOK_PAY_LIST(BOOK_PAY_CODE, BOOK_CODE, BOOK_PAY_DATE)
VALUES(F_CODE('BP', BP_SEQ.NEXTVAL), 'BC000006', SYSDATE);


--○ 마일리지 없으면 결제페이지로 이동

select *
from apply_package;

desc book_list;