package com.example.fishmarket.utilis

import androidx.sqlite.db.SimpleSQLiteQuery

object SortUtils {

    fun getSortedQuery(filter: Int): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT t.id,t.created_date,t.status,t.id_restaurant,COALESCE(r.name,'') as restaurant, tr.name as `table`,t.id_table,t.dibakar_date,t.disajikan_date,t.finished_date,t.total_fee,t.no_urut FROM `transaction` t LEFT JOIN restaurant r ON t.id_restaurant = r.id INNER JOIN table_restaurant tr ON t.id_table = tr.id ")
        if (filter == 0) {
            simpleQuery.append("WHERE t.status !=4  ORDER by created_date DESC")
        } else if (filter == 1) {
            simpleQuery.append("WHERE t.status =4 AND DATE(t.created_date/1000,'unixepoch','localtime') = DATE('now','localtime')  ORDER by created_date DESC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }

    fun getSortedQueryMenu(id: String): SimpleSQLiteQuery {
        val simpleQuery =
            StringBuilder().append("SELECT * FROM menu ")
        if (id == "0") {
            simpleQuery.append("ORDER BY name ASC")
        } else {
            simpleQuery.append("WHERE id_category = '$id' ORDER by name ASC")
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}