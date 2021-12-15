package me.tunaxor.apps.mandadin

import kotlinx.serialization.Serializable
import org.kodein.db.Key
import org.kodein.db.Options
import org.kodein.db.model.Id
import org.kodein.db.model.ModelDB
import org.kodein.db.model.orm.HasMetadata
import org.kodein.db.model.orm.Metadata
import org.kodein.memory.util.UUID

@Serializable
data class Note(
    val uid: UUID,
    val content: String
): HasMetadata {
    override fun getMetadata(db: ModelDB, vararg options: Options.Puts): Metadata {
        return Metadata(uid, content to listOf(uid))
    }
}

@Serializable
data class Category(
    val uid: UUID,
    val name: String,
    val hideDone: Boolean
) : HasMetadata {
    override fun getMetadata(db: ModelDB, vararg options: Options.Puts): Metadata {
        return Metadata(uid, name to listOf(name))
    }
}

@Serializable
data class CategoryItem(
    val uid: UUID,
    val name: String,
    val isDone: Boolean,
    val categoryId: Key<Category>
) : HasMetadata {
    override fun getMetadata(db: ModelDB, vararg options: Options.Puts): Metadata {
        return Metadata(uid, name to listOf(name, isDone))
    }
}