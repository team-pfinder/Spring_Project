-- NOTICE(공지사항, N)
CREATE SEQUENCE N_SEQ
NOCACHE;

-- ATTACHMENT(첨부파일(공지사항), AT)
CREATE SEQUENCE AT_SEQ
NOCACHE;

-- HELP(도움말, HLP)
CREATE SEQUENCE HLP_SEQ
NOCACHE;

-- NOTICE_IMG(이미지(공지사항), NIMG)
CREATE SEQUENCE NIMG_SEQ
NOCACHE;

-- HELP_IMG(이미지(도움말), HLPIMG)
CREATE SEQUENCE HLPIMG_SEQ
NOCACHE;

-- MEMBER(회원(이용자), M)
CREATE SEQUENCE M_SEQ
NOCACHE;

-- MEMBER_WITHDRAW(탈퇴(이용자), MW)
CREATE SEQUENCE MW_SEQ
NOCACHE;

-- HOST(회원(호스트), H) 
CREATE SEQUENCE H_SEQ
NOCACHE;

-- HOST_BLACKLIST(탈퇴(이용자), HW)
CREATE SEQUENCE HW_SEQ
NOCACHE;

-- LOC(공간, L)
CREATE SEQUENCE L_SEQ
NOCACHE;

-- BIZ_INFO(사업자정보, BZ)
CREATE SEQUENCE BZ_SEQ
NOCACHE;

-- LOC_CONTACT(연락처, LC)
CREATE SEQUENCE LC_SEQ
NOCACHE;

-- LOC_DETAIL_INFO
CREATE SEQUENCE LDIF_SEQ
NOCACHE;

-- LOC_DETAIL_IMG
CREATE SEQUENCE LDIMG_SEQ
NOCACHE;

-- LOC_USE_INFO
CREATE SEQUENCE LUI_SEQ
NOCACHE;

--LOC_BASIC_INFO(기본정보, LBIF) 
CREATE SEQUENCE LBIF_SEQ
NOCACHE;

--THUMBNAIL(썸네일(기본정보), TH) 
CREATE SEQUENCE TH_SEQ
NOCACHE;

-- FACILITY_INFO
CREATE SEQUENCE FIF_SEQ
NOCACHE;

-- CAUTION
CREATE SEQUENCE CAU_SEQ
NOCACHE;

-- PACKAGE_FORM
CREATE SEQUENCE PF_SEQ
NOCACHE;

-- PACKAGE(패키지, P)
CREATE SEQUENCE P_SEQ
NOCACHE;

-- APPLY_PACKAGE(적용된 패키지, AP)
CREATE SEQUENCE AP_SEQ
NOCACHE;

-- PACKAGE_REMOVE(폐기 패키지, PRM)
CREATE SEQUENCE PRM_SEQ
NOCACHE;

-- LOC_BLIND(블라인드내역(공간게시물), LB) 
CREATE SEQUENCE LB_SEQ
NOCACHE;

-- LOC_WEB(공간웹사이트(상세정보), LW) 
CREATE SEQUENCE LW_SEQ
NOCACHE;

-- LOC_REMOVE(삭제내역(공간), LRM)
CREATE SEQUENCE LRM_SEQ
NOCACHE;

-- BOOK_LIST(예약내역, BC) 
CREATE SEQUENCE BC_SEQ 
NOCACHE;

-- HOST_CANCEL_LIST(예약취소내역(호스트), HC)
CREATE SEQUENCE HC_SEQ
NOCACHE;

-- BOOK_REFUND_LIST(예약환불내역(이용자))
CREATE SEQUENCE BRF_SEQ
NOCACHE;

--BOOK_PAY_LIST(예약 결제 내역(이용자), BP)
CREATE SEQUENCE BP_SEQ
NOCACHE;

-- ACTUAL_BOOKER(실예약자, AB)
CREATE SEQUENCE AB_SEQ
NOCACHE;
         
-- MEMBER_CANCEL_LIST(예약취소내역(이용자), MC)
CREATE SEQUENCE MC_SEQ
NOCACHE;

-- INSPECT_TYPE(검수분류, IT)
CREATE SEQUENCE IT_SEQ
NOCACHE;

-- INSPECT_REG_LIST(검수신청내역, IR)
CREATE SEQUENCE IR_SEQ
NOCACHE;

-- INSPECT_PROC_LIST(검수처리내역, IP)
CREATE SEQUENCE IP_SEQ
NOCACHE;

-- LOAD_REG(마일리지 충전신청 내역(이용자), LR)
CREATE SEQUENCE LR_SEQ
NOCACHE;

-- LOAD_REG_BANK_INFO(충전신청계좌정보 LRBIF)
CREATE SEQUENCE LRBIF_SEQ
NOCACHE;

-- LOAD_PROC(충전처리내역(이용자), LP)
CREATE SEQUENCE LP_SEQ
NOCACHE;

-- MEMBER_EXCHANGE_LIST(마일리지 환전 내역(이용자), ME)
CREATE SEQUENCE ME_SEQ
NOCACHE;

--MEMBER_EXCHANGE_BANK_INFO(환전계좌정보(이용자), MEBIF)
CREATE SEQUENCE MEBIF_SEQ
NOCACHE;

-- HOST_EXCHANGE_LIST(마일리지 환전 내역(호스트), HE)
CREATE SEQUENCE HE_SEQ
NOCACHE;

--HOST_EXCHANGE_BANK_INFO(환전계좌정보(호스트), HEBIF)
CREATE SEQUENCE HEBIF_SEQ
NOCACHE;

-- QNA(QNA(이용자), Q)
CREATE SEQUENCE Q_SEQ
NOCACHE;

-- QNA_REMOVE(삭제내역(Q&A), QRM)
CREATE SEQUENCE QRM_SEQ
NOCACHE;

-- QNA_REPLY(Q&A답글(호스트), QRE)
CREATE SEQUENCE QRE_SEQ
NOCACHE;

-- QNA_REPLY_REMOVE(삭제내역(Q&A답글), QRERM)
CREATE SEQUENCE QRERM_SEQ
NOCACHE;

-- REVIEW(리뷰(이용자), RV)
CREATE SEQUENCE RV_SEQ
NOCACHE;

-- REVIEW_IMG(이미지(리뷰), RVIMG)
CREATE SEQUENCE RVIMG_SEQ
NOCACHE;

-- REVIEW_REMOVE(삭제내역(리뷰), RVRM)
CREATE SEQUENCE RVRM_SEQ
NOCACHE;

-- REVIEW_REPLY(리뷰답글(호스트), RVRE)
CREATE SEQUENCE RVRE_SEQ
NOCACHE;

-- REVIEW_REPLY_REMOVE(삭제내역(리뷰답글), RVRERM)
CREATE SEQUENCE RVRERM_SEQ
NOCACHE;

-- BOOK_REPORT(신고접수(이용자)(예약), BRP)
CREATE SEQUENCE BRP_SEQ
NOCACHE;

-- BOOK_REPORT_IMG(이미지(신고), BRPIMG)
CREATE SEQUENCE BRPIMG_SEQ
NOCACHE;

-- BOOK_REPORT_PROC(신고처리(이용자)(예약), BRPP)
CREATE SEQUENCE BRPP_SEQ
NOCACHE;

-- LOC_REPORT(신고접수(이용자)(공간), LRP)
CREATE SEQUENCE LRP_SEQ
NOCACHE;

-- LOC_REPORT_IMG(이미지(신고), LRPIMG)
CREATE SEQUENCE LRPIMG_SEQ
NOCACHE;

-- LOC_REPORT_PROC(신고처리(이용자)(공간), LRPP)
CREATE SEQUENCE LRPP_SEQ
NOCACHE;

--MSG(메신저,M)
CREATE SEQUENCE MSG_SEQ
NOCACHE;

--MEMBER_MSG_INFO(메신저정보(이용자) MMIF)
CREATE SEQUENCE MMIF_SEQ
NOCACHE;

--MEMBER_MSG_IMG(이미지(메신저정보(이용자)) MMIMG)
CREATE SEQUENCE MMIMG_SEQ
NOCACHE;

--HOST_MSG_INFO(메신저정보(호스트) HMIF)
CREATE SEQUENCE HMIF_SEQ
NOCACHE;

--HOST_MSG_IMG(이미지(메신저정보(호스트)) HMIMG)
CREATE SEQUENCE HMIMG_SEQ
NOCACHE;





