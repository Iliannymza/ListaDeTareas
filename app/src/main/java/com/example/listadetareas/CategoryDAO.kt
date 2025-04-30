package com.example.listadetareas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class CategoryDAO(private val context: Context) {

    private lateinit var db: SQLiteDatabase

    private fun open() {
        db = DatabaseManager(context).writableDatabase
    }

    private fun  close() {
        db.close()

    // Insertar
    fun insert(category: Category) {
        open()

        try {
         // Create a new map of values, where column names are the keys
         val values = ContentValues()
         values.put(Category.COLUMN_NAME_TITLE, category.title)

    // Insert the new row, returning the primary key value of the new row
         val newRowId = db.insert(Category.TABLE_NAME, null, values)

         Log.i("DATABASE", "Inserted a category with id: $newRowId")
     } catch (e: Exception) {
         e.printStackTrace()
     } finally {
         close()
     }
 }

    // Actualizar
        fun update (category: Category) {
            open()

        try {
            // Create a new map of values, where column names are the keys
            val values = ContentValues()
            values.put(Category.COLUMN_NAME_TITLE, category.title)

            // Which row to update, based on the title
            val selection = "${Category.COLUMN_NAME_ID} = ${category.id}"

            val count = db.update(Category.TABLE_NAME, values, selection, null)

            Log.i("DATABASE", "Update category with id: ${category.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
            }
        }

        // Borrar
       fun  delete(category: Category) {
            open()

            try {
                // Create a new map of values, where column names are the keys
                val values = ContentValues()
                values.put(Category.COLUMN_NAME_TITLE, category.title)

            // Define 'where' part of query.
            val selection = "${FeedEntry.COLUMN_NAME_TITLE} LIKE ?"
            // Specify arguments in placeholder order.
            val selectionArgs = arrayOf("MyTitle")
            // Issue SQL statement.
            val deletedRows = db.delete(FeedEntry.TABLE_NAME, selection, selectionArgs)

            Log.i("DATABASE", "Update category with id: ${category.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
            }
        }

    // Obtener registro por ID

    // Obtener todos los registros
}