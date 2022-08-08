package com.project.newsapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.project.newsapp.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
//entities specifies the tables that the database contains
//version specifies a current version. Once we introduce/modify the new version, we have to increase the version and define the migration strategy.
//exportSchema option is for dumping a database schema to file system. We do not need that.
public abstract class NewsAppDatabase extends RoomDatabase {
    //the class is abstract, because we do not need to implement it, Room annotation processor will.
    public abstract ArticleDao articleDao();
}
