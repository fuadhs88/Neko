package eu.kanade.tachiyomi.data.database.mappers

import android.content.ContentValues
import android.database.Cursor
import com.pushtorefresh.storio.sqlite.SQLiteTypeMapping
import com.pushtorefresh.storio.sqlite.operations.delete.DefaultDeleteResolver
import com.pushtorefresh.storio.sqlite.operations.get.DefaultGetResolver
import com.pushtorefresh.storio.sqlite.operations.put.DefaultPutResolver
import com.pushtorefresh.storio.sqlite.queries.DeleteQuery
import com.pushtorefresh.storio.sqlite.queries.InsertQuery
import com.pushtorefresh.storio.sqlite.queries.UpdateQuery
import eu.kanade.tachiyomi.data.database.models.MangaSimilar
import eu.kanade.tachiyomi.data.database.models.MangaSimilarImpl
import eu.kanade.tachiyomi.data.database.tables.SimilarTable.COL_ID
import eu.kanade.tachiyomi.data.database.tables.SimilarTable.COL_MANGA_ID
import eu.kanade.tachiyomi.data.database.tables.SimilarTable.COL_MANGA_SIMILAR_MATCHED_IDS
import eu.kanade.tachiyomi.data.database.tables.SimilarTable.COL_MANGA_SIMILAR_MATCHED_TITLES
import eu.kanade.tachiyomi.data.database.tables.SimilarTable.TABLE

class SimilarTypeMapping : SQLiteTypeMapping<MangaSimilar>(
    SimilarPutResolver(),
    SimilarGetResolver(),
    SimilarDeleteResolver()
)

class SimilarPutResolver : DefaultPutResolver<MangaSimilar>() {

    override fun mapToInsertQuery(obj: MangaSimilar) = InsertQuery.builder()
        .table(TABLE)
        .build()

    override fun mapToUpdateQuery(obj: MangaSimilar) = UpdateQuery.builder()
        .table(TABLE)
        .where("$COL_ID = ?")
        .whereArgs(obj.id)
        .build()

    override fun mapToContentValues(obj: MangaSimilar) = ContentValues(4).apply {
        put(COL_ID, obj.id)
        put(COL_MANGA_ID, obj.manga_id)
        put(COL_MANGA_SIMILAR_MATCHED_IDS, obj.matched_ids)
        put(COL_MANGA_SIMILAR_MATCHED_TITLES, obj.matched_titles)
    }
}

class SimilarGetResolver : DefaultGetResolver<MangaSimilar>() {

    override fun mapFromCursor(cursor: Cursor): MangaSimilar = MangaSimilarImpl().apply {
        id = cursor.getLong(cursor.getColumnIndex(COL_ID))
        manga_id = cursor.getLong(cursor.getColumnIndex(COL_MANGA_ID))
        matched_ids = cursor.getString(cursor.getColumnIndex(COL_MANGA_SIMILAR_MATCHED_IDS))
        matched_titles = cursor.getString(cursor.getColumnIndex(COL_MANGA_SIMILAR_MATCHED_TITLES))
    }
}

class SimilarDeleteResolver : DefaultDeleteResolver<MangaSimilar>() {

    override fun mapToDeleteQuery(obj: MangaSimilar) = DeleteQuery.builder()
        .table(TABLE)
        .where("$COL_ID = ?")
        .whereArgs(obj.id)
        .build()
}
