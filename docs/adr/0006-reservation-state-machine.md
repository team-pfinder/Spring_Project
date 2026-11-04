# 예약 상태는 단일 컬럼 + 이벤트 이력으로 관리한다

레거시는 취소·환불·블라인드·삭제를 각각 별도 테이블에 행을 삽입해 표현했고, 예약 상태를 알려면 4~5개 테이블을 조회해야 했다. 이를 `reservations.status` 단일 컬럼과 append-only `reservation_events`(from, to, actor, reason, at)로 바꾼다.

- 상태: `PENDING_APPROVAL → PENDING_PAYMENT → CONFIRMED → COMPLETED`, 파생 `CANCELLED`, `REFUNDED`, `REJECTED`, `EXPIRED`.
- 승인예약을 범위에 포함한다. 승인 시한 24시간, 승인 후 미결제 만료 1시간(설정값).
- 이용완료 전이는 매일 00:05 KST 배치(`end_at` 경과)로 처리하고, 호스트가 조기 완료할 수 있다.
- 대안: 3단계(예약완료·이용완료·취소), 상태별 satellite 테이블 유지. 전자는 환불 상태를 표현하지 못하고, 후자는 조회 복잡도를 그대로 안고 가 기각.
- 결과: 예약 1건의 상태 확인이 단일 행 조회가 되고, 모든 전이가 감사 가능해진다.
