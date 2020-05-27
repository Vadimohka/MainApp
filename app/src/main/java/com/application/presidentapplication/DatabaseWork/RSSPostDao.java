package com.application.presidentapplication.DatabaseWork;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.application.presidentapplication.Models.RSSPost;

import java.util.List;

@Dao
public interface RSSPostDao {
    @Insert
    void insert(RSSPost rssPost);
    @Query("DELETE FROM rsspost")
    void deleteAll();
    @Query("SELECT * FROM rsspost")
    List<RSSPost> getAll();
}
