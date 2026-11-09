package com.lookation.repository

import com.lookation.domain.AttachmentOwnerType
import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
class AttachmentRepository(private val dsl: DSLContext) {

    fun insert(
        ownerType: AttachmentOwnerType,
        ownerId: Long,
        storageKey: String,
        originalName: String,
        contentType: String,
        size: Long,
    ) {
        lockOwner(ownerType, ownerId)
        dsl.insertInto(ATTACHMENTS)
            .set(ATTACHMENTS.OWNER_TYPE, ownerType.name)
            .set(ATTACHMENTS.OWNER_ID, ownerId)
            .set(ATTACHMENTS.STORAGE_KEY, storageKey)
            .set(ATTACHMENTS.ORIGINAL_NAME, originalName)
            .set(ATTACHMENTS.CONTENT_TYPE, contentType)
            .set(ATTACHMENTS.SIZE_BYTES, size)
            .set(ATTACHMENTS.SORT_ORDER, nextSortOrder(ownerType, ownerId))
            .execute()
    }

    // 같은 소유자에 동시 업로드가 겹쳐도 순번이 겹치지 않게 트랜잭션 범위로 직렬화한다.
    private fun lockOwner(ownerType: AttachmentOwnerType, ownerId: Long) {
        dsl.resultQuery(
            "select pg_advisory_xact_lock(hashtextextended(? || ':' || ?, 0))",
            ownerType.name,
            ownerId.toString(),
        ).execute()
    }

    private fun nextSortOrder(ownerType: AttachmentOwnerType, ownerId: Long): Short {
        val max = dsl.select(DSL.max(ATTACHMENTS.SORT_ORDER))
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.OWNER_TYPE.eq(ownerType.name))
            .and(ATTACHMENTS.OWNER_ID.eq(ownerId))
            .fetchOne(0, Int::class.java)
        return ((max ?: -1) + 1).toShort()
    }
}
