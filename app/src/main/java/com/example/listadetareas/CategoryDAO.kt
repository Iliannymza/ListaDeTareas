package com.example.listadetareas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
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

            // Which row to update, based on the id
            val selection = "${Category.COLUMN_NAME_ID} = ${category.id}"

            // Update the new row, returning the primary key value of the new row
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
                // Define ´where´ part of query.
            val selection = "${Category.COLUMN_NAME_ID}  ${category.id}"

                // Issue SQL statement
            val deletedRows = db.delete(Category.TABLE_NAME, selection, null)

            Log.i("DATABASE", "Delete category with id: ${category.id}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
            }
        }

        // Obtener registro por ID
        fun findById(id: Long): Category? {
            open()

            var category: Category? = null
            try {
                val projection = arrayOf(
                    Category.COLUMN_NAME_ID,
                    Category.COLUMN_NAME_TITLE
                )

                // FILTER RESULTS WHERE "id" = category.id
                val selection = "${Category.COLUMN_NAME_ID}  $id"

                val cursor = db.query(
                    Category.TABLE_NAME,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    null

                )

                if (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_TITLE))

                    category = Category(id, title)

                }
            } catch (e: Exception) {
                    e.printStackTrace()
            } finally {
                close()
            }

            return  category
        }

        // Obtener todos los registros
        fun findAll(): List<Category> {open()

            var categoryList: MutableList<Category> = mutableListOf()
            try {
                val projection = arrayOf(
                    Category.COLUMN_NAME_ID,
                    Category.COLUMN_NAME_TITLE
                )

                // FILTER RESULTS WHERE "id" = category.id
                val selection = null

                val cursor = db.query(
                    Category.TABLE_NAME,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    null

                )

                while (cursor.moveToNext()) {
                    val id = cursor.getLong(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_ID))
                    val title = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME_TITLE))

                   val category = Category(id, title)
                    categoryList.add(category)

                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                close()
            }

            return categoryList

        }
}