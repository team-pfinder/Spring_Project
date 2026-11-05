package com.lookation.repository

import com.lookation.jooq.tables.Attachments.ATTACHMENTS
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.springframework.stereotype.Repository

@Repository
class AttachmentRepository(private val dsl: DSLContext) {

    fun insert(
        ownerType: String,
        ownerId: Long,
        storageKey: String,
        originalName: String,
        contentType: String,
        size: Long,
    ) {
        dsl.insertInto(ATTACHMENTS)
            .set(ATTACHMENTS.OWNER_TYPE, ownerType)
            .set(ATTACHMENTS.OWNER_ID, ownerId)
            .set(ATTACHMENTS.STORAGE_KEY, storageKey)
            .set(ATTACHMENTS.ORIGINAL_NAME, originalName)
            .set(ATTACHMENTS.CONTENT_TYPE, contentType)
            .set(ATTACHMENTS.SIZE_BYTES, size)
            .set(ATTACHMENTS.SORT_ORDER, nextSortOrder(ownerType, ownerId))
            .execute()
    }

    private fun nextSortOrder(ownerType: String, ownerId: Long): Short {
        val max = dsl.select(DSL.max(ATTACHMENTS.SORT_ORDER))
            .from(ATTACHMENTS)
            .where(ATTACHMENTS.OWNER_TYPE.eq(ownerType))
            .and(ATTACHMENTS.OWNER_ID.eq(ownerId))
            .fetchOne(0, Int::class.java)
        return ((max ?: -1) + 1).toShort()
    }
}
