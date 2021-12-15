package me.tunaxor.apps.mandadin

import org.kodein.db.*
import org.kodein.db.impl.open
import org.kodein.db.orm.kotlinx.KotlinxSerializer
import org.kodein.memory.util.UUID


private var db: DB? = null;

fun initDb(path: String) {
    db = DB.open(path, KotlinxSerializer {
        +Note.serializer()
        +Category.serializer()
        +CategoryItem.serializer()
    })
}

class Notesdb {
    companion object {
        fun find(): List<Note> {
            if (db == null) return listOf()
            return db!!.find<Note>().all().useModels {
                it.toList()
            }
        }

        fun findOne(uid: UUID): Note? {
            return db?.getById(uid)
        }

        fun save(note: Note): Key<Note>? {
            return db?.put(note)
        }

        fun delete(note: UUID) {
            db?.deleteById<Note>(note)
        }

        fun deleteAll() {
            db?.find<Note>()?.all()?.useModels {
                it.forEach {
                    db?.deleteById<Note>(it.uid)
                }
            }
        }
    }
}