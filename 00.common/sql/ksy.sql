SELECT USER
FROM DUAL;
--==>> LOOKATION_LOCAL




--○ userQnaManager 페이지

--○ 기본정보 뷰(VIEW) 생성
CREATE OR REPLACE VIEW ADMIN_QNA_BASIC_VIEW
AS
SELECT Q.QNA_CODE, MP.MEMBER_NICKNAME, MP.MEMBER_EMAIL, Q.LOC_CODE
     , Q.QNA_CONTENT
     , (SELECT COUNT(*)
        FROM QNA_REMOVE
        WHERE QNA_CODE = Q.QNA_CODE) AS COUNT
FROM QNA Q JOIN MEMBER_PROFILE MP
ON Q.MEMBER_CODE = MP.MEMBER_CODE;
--==>> View ADMIN_QNA_BASIC_VIEW이(가) 생성되었습니다.

--○ 확인
SELECT QNA_CODE, MEMBER_NICKNAME, MEMBER_EMAIL, LOC_CODE, QNA_CONTENT, COUNT
FROM ADMIN_QNA_BASIC_VIEW;
--> 한 줄 구성
SELECT QNA_CODE, MEMBER_NICKNAME, MEMBER_EMAIL, LOC_CODE, QNA_CONTENT,COUNT FROM ADMIN_QNA_BASIC_VIEW
;


--○ 상세보기 뷰(VIEW) 생성
CREATE OR REPLACE VIEW ADMIN_QNA_DETAIL_VIEW
AS
SELECT Q.QNA_CODE, Q.LOC_CODE, Q.QNA_DATE, MP.MEMBER_NICKNAME, Q.QNA_CONTENT
FROM QNA Q
JOIN MEMBER_PROFILE MP
ON Q.MEMBER_CODE = MP.MEMBER_CODE;
--==>> View ADMIN_QNA_DETAIL_VIEW이(가) 생성되었습니다.

--○ 확인
SELECT LOC_CODE, QNA_DATE, MEMBER_NICKNAME, QNA_CONTENT
FROM ADMIN_QNA_DETAIL_VIEW
WHERE QNA_CODE = 'Q000001';
--> 한 줄 구성
SELECT LOC_CODE, QNA_DATE, MEMBER_NICKNAME, QNA_CONTENT FROM ADMIN_QNA_DETAIL_VIEW WHERE QNA_CODE = 'Q000001'
;

--○ QNA 삭제 될 때 쿼리문
INSERT INTO QNA_REMOVE(QNA_REMOVE_CODE, QNA_CODE, QNA_REMOVE_DATE)
VALUES (F_CODE('QRM', QRM_SEQ.NEXTVAL), 'Q000001', SYSDATE);
--> 한 줄 구성
INSERT INTO QNA_REMOVE(QNA_REMOVE_CODE, QNA_CODE, QNA_REMOVE_DATE) VALUES (QRM_SEQ.NEXTVAL, 'Q000001', SYSDATE)
;


----------------------------------------------------------------------------------


--○ USERREVIEWMANAGER 페이지

--○ 기본정보 뷰(VIEW) 생성
CREATE OR REPLACE VIEW ADMIN_REVIEW_BASIC_VIEW
AS
SELECT R.REVIEW_CODE, MP.MEMBER_NICKNAME, MP.MEMBER_EMAIL
     , R.LOC_CODE, R.REVIEW_CONTENT
     , (SELECT COUNT(*)
        FROM REVIEW_REMOVE
        WHERE REVIEW_CODE = R.REVIEW_CODE) AS COUNT
FROM REVIEW R JOIN MEMBER_PROFILE MP
ON R.MEMBER_CODE = MP.MEMBER_CODE;
--==>> View ADMIN_REVIEW_BASIC_VIEW이(가) 생성되었습니다.

--○ 뷰 조회
SELECT REVIEW_CODE, MEMBER_NICKNAME, MEMBER_EMAIL
     , LOC_CODE, REVIEW_CONTENT, COUNT
FROM ADMIN_REVIEW_BASIC_VIEW;


--○ 상세정보 뷰(VIEW) 생성
CREATE OR REPLACE VIEW ADMIN_REVIEW_DETAIL_VIEW
AS
SELECT R.REVIEW_CODE, R.LOC_CODE, R.REVIEW_DATE, MP.MEMBER_NICKNAME, R.REVIEW_CONTENT
FROM REVIEW R JOIN MEMBER_PROFILE MP
ON R.MEMBER_CODE = MP.MEMBER_CODE;
--==>> View ADMIN_REVIEW_DETAIL_VIEW이(가) 생성되었습니다.

--○ 뷰 조회
SELECT LOC_CODE, REVIEW_DATE, MEMBER_NICKNAME, REVIEW_CONTENT
FROM ADMIN_REVIEW_DETAIL_VIEW
WHERE REVIEW_CODE = 'RV000001';


--○ REVIEW 삭제 테이블 추가 쿼리문 구성
INSERT INTO REVIEW_REMOVE (REVIEW_REMOVE_CODE, REVIEW_CODE, REVIEW_REMOVE_DATE)
VALUES(F_CODE('RVRM', RVRM_SEQ.NEXTVAL), 'RV000001', SYSDATE);





---------------------------------------------------------------------------------------


--○ REPORTLIST 페이지


--○ 이용자 → 호스트 신고 (공간 신고) 기본정보 뷰(VIEW) 생성
CREATE OR REPLACE VIEW LOC_REPORT_BASIC_VIEW
AS
SELECT LR.LOC_CODE, LRT.LOC_REPORT_TYPE, HP.HOST_NICKNAME
     , MP.MEMBER_NICKNAME, LR.LOC_REPORT_DATE, HP.HOST_EMAIL, MP.MEMBER_EMAIL
     , (SELECT COUNT(*)
        FROM LOC_REPORT_PROC
        WHERE LOC_REPORT_CODE = LR.LOC_REPORT_CODE) AS RESULT_COUNT
     , (SELECT COUNT(*)
        FROM HOST_BLACKLIST
        WHERE HOST_EMAIL = HP.HOST_EMAIL) AS BLACK_COUNT
    , LOC_REPORT_CODE 
FROM LOC_REPORT_TYPE LRT JOIN LOC_REPORT LR
ON LRT.LOC_REPORT_TYPE_CODE = LR.LOC_REPORT_TYPE_CODE
JOIN LOC L
ON LR.LOC_CODE = L.LOC_CODE
JOIN HOST_PROFILE HP
ON HP.HOST_CODE = L.HOST_CODE
JOIN MEMBER_PROFILE MP
ON MP.MEMBER_CODE = LR.MEMBER_CODE;
--==>> View LOC_REPORT_BASIC_VIEW이(가) 생성되었습니다.


--○ 기본정보 뷰 조회 쿼리문
SELECT LOC_CODE, LOC_REPORT_TYPE, HOST_EMAIL, MEMBER_EMAIL
     , LOC_REPORT_DATE, RESULT_COUNT, BLACK_COUNT, LOC_REPORT_CODE
     , HOST_NICKNAME, MEMBER_NICKNAME
FROM LOC_REPORT_BASIC_VIEW
ORDER BY LOC_REPORT_CODE DESC;

--○ 상세보기 뷰 생성 → RESULT_COUNT == 0 일 때 보여지는 상세보기 팝업창
CREATE OR REPLACE VIEW LOC_REPORT_DETAIL_VIEW_0
AS
SELECT LR.LOC_CODE, LRT.LOC_REPORT_TYPE, HP.HOST_EMAIL
     , MP.MEMBER_EMAIL, LR.LOC_REPORT_REASON, LR.LOC_REPORT_DATE
     , NVL(LRI.LOC_REPORT_IMG_URL, '#') AS LOC_REPORT_IMG_URL, LR.LOC_REPORT_CODE
     , HP.HOST_NICKNAME, MP.MEMBER_NICKNAME
FROM LOC_REPORT_TYPE LRT JOIN LOC_REPORT LR
ON LRT.LOC_REPORT_TYPE_CODE = LR.LOC_REPORT_TYPE_CODE
JOIN LOC L
ON LR.LOC_CODE = L.LOC_CODE
JOIN HOST_PROFILE HP
ON HP.HOST_CODE = L.HOST_CODE
JOIN MEMBER_PROFILE MP
ON MP.MEMBER_CODE = LR.MEMBER_CODE
JOIN LOC_REPORT_IMG LRI
ON LR.LOC_REPORT_CODE = LRI.LOC_REPORT_CODE;
--==>> View LOC_REPORT_DETAIL_VIEW_0이(가) 생성되었습니다.


--○ 상세보기(RESULT_COUNT == 0) 조회 쿼리문
SELECT LOC_CODE, LOC_REPORT_TYPE, HOST_EMAIL, MEMBER_EMAIL
     , LOC_REPORT_REASON, LOC_REPORT_DATE, LOC_REPORT_IMG_URL, LOC_REPORT_CODE
     , HOST_NICKNAME, MEMBER_NICKNAME
FROM LOC_REPORT_DETAIL_VIEW_0
WHERE LOC_REPORT_CODE = 'LRP000001';


--○ 상세보기 뷰 생성 → RESULT_COUNT == 1 일 때 보여지는 상세보기 팝업창
CREATE OR REPLACE VIEW LOC_REPORT_DETAIL_VIEW_1
AS
SELECT LR.LOC_CODE, LRT.LOC_REPORT_TYPE, HP.HOST_EMAIL
     , MP.MEMBER_EMAIL, LR.LOC_REPORT_REASON, LR.LOC_REPORT_DATE
     , NVL(LRI.LOC_REPORT_IMG_URL, '#') AS LOC_REPORT_IMG_URL, LR.LOC_REPORT_CODE
     , RPT.REPORT_PROC_TYPE, LRP.LOC_REPORT_PROC_DATE
     , (SELECT COUNT(*)
        FROM HOST_BLACKLIST
        WHERE HOST_EMAIL = HP.HOST_EMAIL) AS BLACK_COUNT
     , HP.HOST_NICKNAME, MP.MEMBER_NICKNAME
FROM LOC_REPORT_TYPE LRT JOIN LOC_REPORT LR
ON LRT.LOC_REPORT_TYPE_CODE = LR.LOC_REPORT_TYPE_CODE
JOIN LOC L
ON LR.LOC_CODE = L.LOC_CODE
JOIN HOST_PROFILE HP
ON HP.HOST_CODE = L.HOST_CODE
JOIN MEMBER_PROFILE MP
ON MP.MEMBER_CODE = LR.MEMBER_CODE
LEFT JOIN LOC_REPORT_IMG LRI
ON LR.LOC_REPORT_CODE = LRI.LOC_REPORT_CODE
JOIN LOC_REPORT_PROC LRP
ON LR.LOC_REPORT_CODE = LRP.LOC_REPORT_CODE
JOIN REPORT_PROC_TYPE RPT
ON RPT.REPORT_PROC_TYPE_CODE = LRP.REPORT_PROC_TYPE_CODE;
--==>> View LOC_REPORT_DETAIL_VIEW_1이(가) 생성되었습니다.


--○ 상세보기(RESULT_COUNT == 1) 조회 쿼리문
SELECT LOC_CODE, LOC_REPORT_TYPE, HOST_EMAIL, MEMBER_EMAIL, LOC_REPORT_REASON
     , LOC_REPORT_DATE, LOC_REPORT_IMG_URL, REPORT_PROC_TYPE
     , LOC_REPORT_PROC_DATE, BLACK_COUNT, LOC_REPORT_CODE
     , HOST_NICKNAME, MEMBER_NICKNAME
FROM LOC_REPORT_DETAIL_VIEW_1
WHERE LOC_REPORT_CODE = 'LRP000002';


--○ 신고처리테이블 추가 쿼리문
INSERT INTO LOC_REPORT_PROC(LOC_REPORT_PROC_CODE, LOC_REPORT_CODE, REPORT_PROC_TYPE_CODE, LOC_REPORT_PROC_DATE)
VALUES(F_CODE('LRPP', LRPP_SEQ.NEXTVAL), 'LRP000002', 'RPPT000001', SYSDATE);


--○ 블랙리스트 추가 쿼리문
INSERT INTO HOST_BLACKLIST(HOST_EMAIL, HOST_BLACKLIST_REASON, HOST_BLACKLIST_DATE)
VALUES('test100@test.com', '과도한 도배로 인한 서비스의 품질 저하', SYSDATE);


--○ 호스트 → 이용자 신고 (예약 신고) 기본정보 뷰(VIEW) 생성
CREATE OR REPLACE VIEW BOOK_REPORT_BASIC_VIEW
AS
SELECT BR.BOOK_CODE, BRT.BOOK_REPORT_TYPE, MP.MEMBER_EMAIL
     , HP.HOST_EMAIL, BR.BOOK_REPORT_DATE
     , (SELECT COUNT(*)
        FROM BOOK_REPORT_PROC
        WHERE BOOK_REPORT_CODE = BR.BOOK_REPORT_CODE) AS RESULT_COUNT
     , (SELECT COUNT(*)
        FROM MEMBER_BLACKLIST
        WHERE MEMBER_EMAIL = MP.MEMBER_EMAIL) AS BLACK_COUNT
    , BR.BOOK_REPORT_CODE
    , HP.HOST_NICKNAME, MP.MEMBER_NICKNAME
FROM BOOK_REPORT_TYPE BRT JOIN BOOK_REPORT BR
ON BRT.BOOK_REPORT_TYPE_CODE = BR.BOOK_REPORT_TYPE_CODE
JOIN BOOK_LIST BL
ON BR.BOOK_CODE = BL.BOOK_CODE
JOIN MEMBER_PROFILE MP
ON BL.MEMBER_CODE = MP.MEMBER_CODE
JOIN HOST_PROFILE HP
ON BR.HOST_CODE = HP.HOST_CODE;
--==>> View BOOK_REPORT_BASIC_VIEW이(가) 생성되었습니다.


--○ 기본정보 뷰 조회 쿼리문
SELECT BOOK_CODE, BOOK_REPORT_TYPE, MEMBER_EMAIL, HOST_EMAIL
     , BOOK_REPORT_DATE, RESULT_COUNT, BLACK_COUNT, BOOK_REPORT_CODE
     , HOST_NICKNAME, MEMBER_NICKNAME
FROM BOOK_REPORT_BASIC_VIEW
ORDER BY BOOK_REPORT_CODE DESC;


--○ 상세보기 뷰 생성 → RESULT_COUNT == 0 일 때 보여지는 상세보기 팝업창
CREATE OR REPLACE VIEW BOOK_REPORT_DETAIL_VIEW_0
AS
SELECT BR.BOOK_CODE, BRT.BOOK_REPORT_TYPE, MP.MEMBER_EMAIL
     , HP.HOST_EMAIL, BR.BOOK_REPORT_DATE, BR.BOOK_REPORT_REASON
     , NVL(BRI.BOOK_REPORT_IMG_URL, '#') AS BOOK_REPORT_IMG_URL, BR.BOOK_REPORT_CODE
     , HP.HOST_NICKNAME, MP.MEMBER_NICKNAME
FROM BOOK_REPORT_TYPE BRT JOIN BOOK_REPORT BR
ON BRT.BOOK_REPORT_TYPE_CODE = BR.BOOK_REPORT_TYPE_CODE
JOIN BOOK_LIST BL
ON BR.BOOK_CODE = BL.BOOK_CODE
JOIN MEMBER_PROFILE MP
ON BL.MEMBER_CODE = MP.MEMBER_CODE
JOIN HOST_PROFILE HP
ON BR.HOST_CODE = HP.HOST_CODE
LEFT JOIN BOOK_REPORT_IMG BRI
ON BR.BOOK_REPORT_CODE = BRI.BOOK_REPORT_CODE;
--==>> View BOOK_REPORT_DETAIL_VIEW_0이(가) 생성되었습니다.

--○ 상세보기(RESULT_COUNT == 0) 조회 쿼리문
SELECT BOOK_CODE, BOOK_REPORT_TYPE, MEMBER_EMAIL, HOST_EMAIL
     , BOOK_REPORT_DATE, BOOK_REPORT_REASON, BOOK_REPORT_IMG_URL, BOOK_REPORT_CODE
     , HOST_NICKNAME, MEMBER_NICKNAME
FROM BOOK_REPORT_DETAIL_VIEW_0
WHERE BOOK_REPORT_CODE = 'BRP000001';


--○ 상세보기 뷰 생성 → RESULT_COUNT == 1 일 때 보여지는 상세보기 팝업창
CREATE OR REPLACE VIEW BOOK_REPORT_DETAIL_VIEW_1
AS
SELECT BR.BOOK_CODE, BRT.BOOK_REPORT_TYPE, MP.MEMBER_EMAIL
     , HP.HOST_EMAIL, BR.BOOK_REPORT_DATE, BR.BOOK_REPORT_REASON
     , NVL(BRI.BOOK_REPORT_IMG_URL, '#') AS BOOK_REPORT_IMG_URL, BR.BOOK_REPORT_CODE
     , RPT.REPORT_PROC_TYPE, BRP.BOOK_REPORT_PROC_DATE
     , (SELECT COUNT(*)
        FROM MEMBER_BLACKLIST
        WHERE MEMBER_EMAIL = MP.MEMBER_EMAIL) AS BLACK_COUNT
    , HP.HOST_NICKNAME, MP.MEMBER_NICKNAME
FROM BOOK_REPORT_TYPE BRT JOIN BOOK_REPORT BR
ON BRT.BOOK_REPORT_TYPE_CODE = BR.BOOK_REPORT_TYPE_CODE
JOIN BOOK_LIST BL
ON BR.BOOK_CODE = BL.BOOK_CODE
JOIN MEMBER_PROFILE MP
ON BL.MEMBER_CODE = MP.MEMBER_CODE
JOIN HOST_PROFILE HP
ON BR.HOST_CODE = HP.HOST_CODE
LEFT JOIN BOOK_REPORT_IMG BRI
ON BR.BOOK_REPORT_CODE = BRI.BOOK_REPORT_CODE
JOIN BOOK_REPORT_PROC BRP
ON BRP.BOOK_REPORT_CODE = BR.BOOK_REPORT_CODE
JOIN REPORT_PROC_TYPE RPT
ON BRP.REPORT_PROC_TYPE_CODE = RPT.REPORT_PROC_TYPE_CODE;
--==>> View BOOK_REPORT_DETAIL_VIEW_1이(가) 생성되었습니다.


--○ 상세보기(RESULT_COUNT == 1) 조회 쿼리문
SELECT BOOK_CODE, BOOK_REPORT_TYPE, MEMBER_EMAIL, HOST_EMAIL, BOOK_REPORT_DATE
     , BOOK_REPORT_REASON, BOOK_REPORT_IMG_URL
     , REPORT_PROC_TYPE, BOOK_REPORT_PROC_DATE, BLACK_COUNT
     , BOOK_REPORT_CODE, HOST_NICKNAME, MEMBER_NICKNAME
FROM BOOK_REPORT_DETAIL_VIEW_1
WHERE BOOK_REPORT_CODE = 'BRP000001';


--○ 신고처리 테이블 추가 쿼리문
INSERT INTO BOOK_REPORT_PROC(BOOK_REPORT_PROC_CODE, BOOK_REPORT_CODE, REPORT_PROC_TYPE_CODE, BOOK_REPORT_PROC_DATE)
VALUES(F_CODE('BRPP', BRPP_SEQ.NEXTVAL), 'BRP000002', 'RPPT000002', SYSDATE);


--○ 블랙리스트 추가 쿼리문
INSERT INTO MEMBER_BLACKLIST(MEMBER_EMAIL, MEMBER_BLACKLIST_REASON, MEMBER_BLACKLIST_DATE)
VALUES('lookation02@test.com', '공간관리가 미흡하여 이용자들에게 계속해서 불쾌감을 줌', SYSDATE);




---------------------------------------------------------------------------------------



--○ BlindManager 페이지


--○ 뷰 생성
CREATE OR REPLACE VIEW BLINDVIEW
AS
SELECT L.LOC_CODE, HP.HOST_EMAIL, HP.HOST_NICKNAME, LT.LOC_TYPE
     , LBI.LOC_NAME, LB.LOC_BLIND_DATE, LB.LOC_BLIND_REASON
     , LB.LOC_BLIND_CODE
FROM LOC_BLIND LB JOIN LOC_BASIC_INFO LBI
ON LB.LOC_CODE = LBI.LOC_CODE
JOIN LOC L
ON LB.LOC_CODE = L.LOC_CODE
JOIN HOST_PROFILE HP
ON L.HOST_CODE = HP.HOST_CODE
JOIN LOC_TYPE LT
ON LBI.LOC_TYPE_CODE = LT.LOC_TYPE_CODE;
--==>> View BLINDVIEW이(가) 생성되었습니다.


--○ 뷰 조회 쿼리문
SELECT LOC_CODE, HOST_EMAIL, HOST_NICKNAME, LOC_TYPE, LOC_NAME
     , LOC_BLIND_DATE, LOC_BLIND_REASON, LOC_BLIND_CODE
FROM BLINDVIEW;


--○ 블라인드 해제 버튼 쿼리문
DELETE FROM LOC_BLIND
WHERE LOC_BLIND_CODE = 'LB000001';


---------------------------------------------------------------------------------------



--○ reporthost 페이지


INSERT INTO BOOK_REPORT(BOOK_REPORT_CODE, BOOK_CODE, BOOK_REPORT_TYPE_CODE
                      , HOST_CODE, BOOK_REPORT_REASON, BOOK_REPORT_DATE)
VALUES(F_CODE('BRP', BRP_SEQ.NEXTVAL), 'BC000002', 'BRPT000004'
    , 'H000005', '신고테스트3', SYSDATE);

SELECT BOOK_REPORT_CODE
FROM BOOK_REPORT
WHERE BOOK_CODE = ''
  AND HOST_CODE ='';
    
SELECT *
FROM BOOK_REPORT;

INSERT INTO BOOK_REPORT_IMG(BOOK_REPORT_IMG_CODE, BOOK_REPORT_CODE, BOOK_REPORT_IMG_URL)
VALUES (F_CODE('BRPIMG', BRPIMG_SEQ.NEXTVAL), 'BRP000003', '신고테스트.jpg');

    
--○ REPORTUSER 페이지

INSERT INTO LOC_REPORT(LOC_REPORT_CODE, LOC_CODE, LOC_REPORT_TYPE_CODE
                     , MEMBER_CODE, LOC_REPORT_REASON, LOC_REPORT_DATE)
VALUES(F_CODE('LRP', LRP_SEQ.NEXTVAL), 'L000004', 'LRPP000003'
     , 'M000003', '신고 테스트3', SYSDATE);

INSERT INTO LOC_REPORT_IMG(LOC_REPORT_IMG_CODE, LOC_REPORT_CODE, LOC_REPORT_IMG_URL)
VALUES(F_CODE('LRPIMG', LRPIMG.NEXTVAL), 'LRP000002', '신고 테스트.jpg');

SELECT LOC_REPORT_CODE
FROM LOC_REPORT
WHERE LOC_CODE = ''
  AND MEMBER_CODE = '';




---------------------------------------------------------------------------------------



--○ search

CREATE OR REPLACE VIEW SEARCHVIEW
AS
SELECT LT.LOC_TYPE, LBI.LOC_NAME, LBI.LOC_ADDR, TN.THUMBNAIL_URL
     , (SELECT COUNT(*)
        FROM REVIEW
        WHERE LOC_CODE = L.LOC_CODE) AS REVIEW_COUNT
     , (SELECT MIN(PACKAGE_PRICE)
        FROM PACKAGE
        WHERE PACKAGE_FORM_CODE = PF.PACKAGE_FORM_CODE
          AND AP.APPLY_DATE BETWEEN SYSDATE AND (SELECT ADD_MONTHS(SYSDATE,3) FROM DUAL)) AS PRICE
     , (SELECT AVG(REVIEW_RATE)
            FROM REVIEW
            WHERE LOC_CODE = L.LOC_CODE) AS AVG_RATE
     , LDI.MAX_PEOPLE , AP.APPLY_DATE
FROM LOC L JOIN LOC_BASIC_INFO LBI
ON L.LOC_CODE = LBI.LOC_CODE
JOIN LOC_TYPE LT
ON LT.LOC_TYPE_CODE = LBI.LOC_TYPE_CODE
JOIN THUMBNAIL TN
ON LBI.LOC_BASIC_INFO_CODE = TN.LOC_BASIC_INFO_CODE
JOIN PACKAGE_FORM PF
ON L.LOC_CODE = PF.LOC_CODE
JOIN LOC_DETAIL_INFO LDI
ON L.LOC_CODE = LDI.LOC_CODE
LEFT JOIN PACKAGE P
ON PF.PACKAGE_FORM_CODE = P.PACKAGE_FORM_CODE
LEFT JOIN APPLY_PACKAGE AP
ON P.PACKAGE_CODE = AP.PACKAGE_CODE;
--==>> View SEARCHVIEW이(가) 생성되었습니다.

--○ 공간타입 OR 주소 OR 공간명으로 검색하는 경우 (검색창 이용 시 쿼리문)
SELECT DISTINCT LOC_TYPE, LOC_NAME, LOC_ADDR, NVL(AVG_RATE, 0) AS AVG_RATE, PRICE, MAX_PEOPLE, THUMBNAIL_URL
FROM SEARCHVIEW
WHERE PRICE IS NOT NULL
  AND (LOC_TYPE LIKE '%일산%'
   OR LOC_NAME LIKE '%일산%'
   OR LOC_ADDR LIKE '%일산%');

--○ 날짜 검색
SELECT DISTINCT LOC_TYPE, LOC_NAME, LOC_ADDR, NVL(AVG_RATE, 0) AS AVG_RATE, PRICE, MAX_PEOPLE, THUMBNAIL_URL
FROM SEARCHVIEW
WHERE APPLY_DATE BETWEEN TO_DATE('2021-01-02', 'YYYY-MM-DD') AND TO_DATE('2021-03-03', 'YYYY-MM-DD')
  AND PRICE IS NOT NULL;

--○ 정렬용...
/*
ORDER BY PRICE DESC;
ORDER BY PRICE;
ORDER BY REVIEW_COUNT;
ORDER BY REVIEW_COUNT DESC;
ORDER BY AVG_RATE;
ORDER BY AVG_RAGE DESC;
*/

DESC PACKAGE;

--○ 가격 구간 검색 (결과 내 검색 적용)
SELECT DISTINCT LOC_TYPE, LOC_NAME, LOC_ADDR, NVL(AVG_RATE, 0) AS AVG_RATE, PRICE, MAX_PEOPLE, THUMBNAIL_URL
FROM (SELECT DISTINCT LOC_TYPE, LOC_NAME, LOC_ADDR, NVL(AVG_RATE, 0) AS AVG_RATE, PRICE, MAX_PEOPLE, THUMBNAIL_URL
        FROM SEARCHVIEW
        WHERE PRICE IS NOT NULL
          AND (LOC_TYPE LIKE '%일산%'
           OR LOC_NAME LIKE '%일산%'
           OR LOC_ADDR LIKE '%일산%'))
WHERE PRICE BETWEEN 0 AND 1000;


--○ 상세검색 한 쿼리로
SELECT DISTINCT LOC_TYPE, LOC_NAME, LOC_ADDR, NVL(AVG_RATE, 0) AS AVG_RATE, PRICE, MAX_PEOPLE, THUMBNAIL_URL
FROM (SELECT DISTINCT LOC_TYPE, LOC_NAME, LOC_ADDR, NVL(AVG_RATE, 0) AS AVG_RATE, PRICE, MAX_PEOPLE, THUMBNAIL_URL, APPLY_DATE
        FROM SEARCHVIEW
        WHERE PRICE IS NOT NULL
          AND (LOC_TYPE LIKE '%%'
           OR LOC_NAME LIKE '%%'
           OR LOC_ADDR LIKE '%%'))
WHERE LOC_TYPE LIKE '%%'
  AND PRICE BETWEEN 0 AND 1000000
  AND LOC_ADDR LIKE '%%'
  AND LOC_ADDR LIKE '%%'
  AND APPLY_DATE BETWEEN TO_DATE('2021-01-02', 'YYYY-MM-DD') AND TO_DATE('2021-03-03', 'YYYY-MM-DD');






----------------------------------------------------------------------------------------




--○ 확인용 더미데이터 추가

SELECT *
FROM qna;

INSERT INTO QNA(QNA_CODE, LOC_CODE, MEMBER_CODE, QNA_CONTENT, QNA_DATE)
VALUES('Q000019', 'L000002', 'M000002', '테스트용더미데이터', SYSDATE);

COMMIT;

SELECT *
FROM LOC_BLIND;

INSERT INTO LOC_BLIND(LOC_BLIND_CODE, LOC_CODE, LOC_BLIND_REASON, LOC_BLIND_DATE)
VALUES(F_CODE('LB', LB_SEQ.NEXTVAL), 'L000001', '확인용더미데이터', SYSDATE);

COMMIT;



-------------------------------------------------------------------------------------


--○ 
--○ MEMBER 프로필 가져오기
CREATE OR REPLACE VIEW MEMBER_PROFILEVIEW
AS
SELECT MP.MEMBER_EMAIL AS EMAIL, MP.MEMBER_CODE AS CODE
     , MP.MEMBER_PW AS PW, MP.MEMBER_NICKNAME AS NICK
     , MP.MEMBER_NAME AS NAME, MP.MEMBER_TEL AS TEL
     , TO_CHAR(M.MEMBER_SIGN_UP_DATE, 'YYYY-MM-DD') AS CREATEDATE
FROM MEMBER_PROFILE MP, MEMBER M
WHERE MP.MEMBER_CODE = M.MEMBER_CODE;


--○ HOST 프로필 가져오기
CREATE OR REPLACE VIEW HOST_PROFILEVIEW
AS
SELECT HP.HOST_EMAIL AS EMAIL, HP.HOST_CODE AS CODE
     , HP.HOST_PW AS PW, HP.HOST_NICKNAME AS NICK
     , HP.HOST_NAME AS NAME, HP.HOST_TEL AS TEL
     , TO_CHAR(H.HOST_SIGN_UP_DATE, 'YYYY-MM-DD') AS CREATEDATE
FROM HOST_PROFILE HP,  HOST H
WHERE HP.HOST_CODE = H.HOST_CODE;



--○ search
-- sysdate로 변경해야 한다.
CREATE OR REPLACE VIEW SEARCHVIEW_BEFORE
AS
SELECT L.LOC_CODE, LT.LOC_TYPE, LBI.LOC_NAME, LBI.LOC_ADDR, TN.THUMBNAIL_URL
     , (SELECT COUNT(*)
        FROM REVIEW
        WHERE LOC_CODE = L.LOC_CODE) AS REVIEW_COUNT
     , (SELECT MIN(PACKAGE_PRICE)
        FROM PACKAGE
       WHERE PACKAGE_FORM_CODE = PF.PACKAGE_FORM_CODE
          AND PACKAGE_CODE = AP.PACKAGE_CODE
          AND AP.APPLY_DATE BETWEEN TO_DATE(SYSDATE, 'YYYY-MM-DD') AND (SELECT ADD_MONTHS(SYSDATE,3) FROM DUAL)) AS PRICE
     , (SELECT AVG(REVIEW_RATE)
            FROM REVIEW
            WHERE LOC_CODE = L.LOC_CODE) AS AVG_RATE
     , LDI.MAX_PEOPLE , AP.APPLY_DATE
FROM LOC L JOIN LOC_BASIC_INFO LBI
ON L.LOC_CODE = LBI.LOC_CODE
JOIN LOC_TYPE LT
ON LT.LOC_TYPE_CODE = LBI.LOC_TYPE_CODE
JOIN THUMBNAIL TN
ON LBI.LOC_BASIC_INFO_CODE = TN.LOC_BASIC_INFO_CODE
JOIN PACKAGE_FORM PF
ON L.LOC_CODE = PF.LOC_CODE
JOIN LOC_DETAIL_INFO LDI
ON L.LOC_CODE = LDI.LOC_CODE
LEFT JOIN PACKAGE P
ON PF.PACKAGE_FORM_CODE = P.PACKAGE_FORM_CODE
LEFT JOIN APPLY_PACKAGE AP
ON P.PACKAGE_CODE = AP.PACKAGE_CODE
LEFT JOIN LOC_REMOVE LR
ON LR.LOC_CODE = L.LOC_CODE
WHERE LR.LOC_REMOVE_CODE IS NULL;


CREATE OR REPLACE VIEW SEARCHVIEW
AS
SELECT LOC_CODE, LOC_TYPE, LOC_NAME, LOC_ADDR, THUMBNAIL_URL
     , REVIEW_COUNT , AVG_RATE
     , MAX_PEOPLE , APPLY_DATE
     , MIN(PRICE)OVER(PARTITION BY LOC_CODE) AS PRICE
FROM SEARCHVIEW_BEFORE;

select *
from searchview;


SELECT DISTINCT LOC_CODE, LOC_TYPE, LOC_NAME, LOC_ADDR,  NVL(TRUNC(AVG_RATE,1), 0) AS AVG_RATE
, MIN(PRICE)OVER(PARTITION BY LOC_CODE), MAX_PEOPLE, THUMBNAIL_URL 
FROM SEARCHVIEW;
