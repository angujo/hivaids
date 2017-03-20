package com.devindicator.hivaids.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.devindicator.hivaids.models.Article;
import com.devindicator.hivaids.models.Category;
import com.devindicator.hivaids.models.NewsItem;
import com.devindicator.hivaids.models.Page;
import com.devindicator.hivaids.models.User;

import java.util.ArrayList;

/**
 * Created by bangujo on 16/03/2017.
 */

public class DBQuery {
    private SQLiteDatabase database;
    private DBHelper dbHelper;

    public DBQuery(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void open() throws SQLiteException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean setUser(User user) {
        User cUser = getUser();
        if (null == cUser) {
            Log.d("DB", "Adding user...");
            return addUser(user);
        }
        Log.d("DB", "Updating user...");
        return updateUser(user);
    }

    private boolean addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("names", user.getNames());
        values.put("email", user.getEmail());
        values.put("age_in_range", user.isBtn15and35());
        values.put("with_hiv", user.isWithHIV());
        values.put("kenyan_resident", user.isKenyanResident());
        values.put("fear_disclosure", user.isFearDisclosing());
        values.put("fear_isolation", user.isFearIsolation());
        values.put("fear_gossip", user.isFearGossip());
        values.put("prefer_hiv_vendor", user.isPreferHIVVendor());
        values.put("fear_positive_result", user.isFearPositiveResult());
        long insertID = database.insert(DBHelper.TABLE_USER, null, values);
        Log.d("DB", "User insert ID: " + insertID);
        return insertID > 0;
    }

    private boolean updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("names", user.getNames());
        values.put("email", user.getEmail());
        values.put("age_in_range", user.isBtn15and35());
        values.put("with_hiv", user.isWithHIV());
        values.put("kenyan_resident", user.isKenyanResident());
        values.put("fear_disclosure", user.isFearDisclosing());
        values.put("fear_isolation", user.isFearIsolation());
        values.put("fear_gossip", user.isFearGossip());
        values.put("prefer_hiv_vendor", user.isPreferHIVVendor());
        values.put("fear_positive_result", user.isFearPositiveResult());
        long insertID = database.update(DBHelper.TABLE_USER, values, "id = ?", new String[]{user.getId() + ""});
        Log.d("DB", "User update ID: " + insertID);
        return insertID > 0;
    }

    public User getUser() {
        Cursor cursor = database.query(DBHelper.TABLE_USER, DBHelper.COLUMN_USER, null, null, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return null;
        User user = new User();
        user.setId(cursor.getInt(cursor.getColumnIndex("id")));
        user.setNames(cursor.getString(cursor.getColumnIndex("names")));
        user.setEmail(cursor.getString(cursor.getColumnIndex("email")));
        user.setBtn15and35(0 < cursor.getInt(cursor.getColumnIndex("age_in_range")));
        user.setWithHIV(0 < cursor.getInt(cursor.getColumnIndex("with_hiv")));
        user.setKenyanResident(0 < cursor.getInt(cursor.getColumnIndex("kenyan_resident")));
        user.setFearDisclosing(0 < cursor.getInt(cursor.getColumnIndex("fear_disclosure")));
        user.setFearIsolation(0 < cursor.getInt(cursor.getColumnIndex("fear_isolation")));
        user.setFearGossip(0 < cursor.getInt(cursor.getColumnIndex("fear_gossip")));
        user.setPreferHIVVendor(0 < cursor.getInt(cursor.getColumnIndex("prefer_hiv_vendor")));
        user.setFearPositiveResult(0 < cursor.getInt(cursor.getColumnIndex("fear_positive_result")));
        cursor.close();
        return user;
    }

    public boolean setPage(Page page) {
        Page cPage = getPage(page.getType());
        if (null == cPage) {
            return addPage(page);
        }
        return updatePage(page);
    }

    private boolean addPage(Page page) {
        ContentValues values = new ContentValues();
        values.put("id", page.getId());
        values.put("ptype", page.getType());
        values.put("title", page.getTitle());
        values.put("content", page.getContent());
        long insertID = database.insert(DBHelper.TABLE_PAGES, null, values);
        return insertID > 0;
    }

    private boolean updatePage(Page page) {
        ContentValues values = new ContentValues();
        values.put("ptype", page.getType());
        values.put("title", page.getTitle());
        values.put("content", page.getContent());
        long insertID = database.update(DBHelper.TABLE_PAGES, values, "id = ?", new String[]{page.getId() + ""});
        return insertID > 0;
    }

    public Page getPage(String ptype) {
        Cursor cursor = database.query(DBHelper.TABLE_PAGES, new String[]{"id", "ptype", "title", "content"}, "ptype = ?", new String[]{ptype}, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return null;
        Page page = new Page();
        page.setId(cursor.getInt(cursor.getColumnIndex("id")));
        page.setContent(cursor.getString(cursor.getColumnIndex("content")));
        page.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        page.setType(cursor.getString(cursor.getColumnIndex("ptype")));
        cursor.close();
        return page;
    }

    public boolean setNewsItem(NewsItem newsItem) {
        NewsItem item = getNewsItem(newsItem.getId());
        if (null == item) {
            return addNewsItem(newsItem);
        }
        return updateNewsItem(newsItem);
    }

    private boolean addNewsItem(NewsItem newsItem) {
        ContentValues values = new ContentValues();
        values.put("id", newsItem.getId());
        values.put("title", newsItem.getTitle());
        values.put("dated", newsItem.getDateTime());
        values.put("source", newsItem.getSource());
        values.put("link", newsItem.getLink());
        values.put("thumbnail_link", newsItem.getThumbUrl());
        long insertID = database.insert(DBHelper.TABLE_NEWS, null, values);
        return insertID > 0;
    }

    private boolean updateNewsItem(NewsItem newsItem) {
        ContentValues values = new ContentValues();
        values.put("title", newsItem.getTitle());
        values.put("dated", newsItem.getDateTime());
        values.put("source", newsItem.getSource());
        values.put("link", newsItem.getLink());
        values.put("thumbnail_link", newsItem.getThumbUrl());
        long insertID = database.update(DBHelper.TABLE_NEWS, values, "id = ?", new String[]{newsItem.getId() + ""});
        return insertID > 0;
    }

    public NewsItem getNewsItem(int id) {
        Cursor cursor = database.query(DBHelper.TABLE_NEWS, new String[]{"id", "source", "title", "link", "thumbnail_link", "dated"}, "id = ?", new String[]{id + ""}, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return null;
        NewsItem newsItem = new NewsItem();
        newsItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
        newsItem.setDateTime(cursor.getString(cursor.getColumnIndex("dated")));
        newsItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        newsItem.setSource(cursor.getString(cursor.getColumnIndex("source")));
        newsItem.setThumbUrl(cursor.getString(cursor.getColumnIndex("thumbnail_link")));
        newsItem.setLink(cursor.getString(cursor.getColumnIndex("link")));
        cursor.close();
        return newsItem;
    }

    public ArrayList<NewsItem> getNewsItems() {
        ArrayList<NewsItem> newsItemArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_NEWS, new String[]{"id", "source", "title", "link", "thumbnail_link", "dated"}, null, null, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return newsItemArrayList;
        do {
            NewsItem newsItem = new NewsItem();
            newsItem.setId(cursor.getInt(cursor.getColumnIndex("id")));
            newsItem.setDateTime(cursor.getString(cursor.getColumnIndex("dated")));
            newsItem.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            newsItem.setSource(cursor.getString(cursor.getColumnIndex("source")));
            newsItem.setThumbUrl(cursor.getString(cursor.getColumnIndex("thumbnail_link")));
            newsItem.setLink(cursor.getString(cursor.getColumnIndex("link")));
            newsItemArrayList.add(newsItem);
        } while (cursor.moveToNext());
        cursor.close();
        return newsItemArrayList;
    }

    public boolean setCategory(Category category) {
        Category item = getCategory(category.getId());
        if (null == item) {
            return addCategory(category);
        }
        return updateCategory(category);
    }

    private boolean addCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("id", category.getId());
        values.put("title", category.getTitle());
        values.put("description", category.getDescription());
        long insertID = database.insert(DBHelper.TABLE_CATEGORIES, null, values);
        return insertID > 0;
    }

    private boolean updateCategory(Category category) {
        ContentValues values = new ContentValues();
        values.put("title", category.getTitle());
        values.put("description", category.getDescription());
        long insertID = database.update(DBHelper.TABLE_CATEGORIES, values, "id = ?", new String[]{category.getId() + ""});
        return insertID > 0;
    }

    public Category getCategory(int id) {
        Cursor cursor = database.query(DBHelper.TABLE_CATEGORIES, new String[]{"id", "description", "title"}, "id = ?", new String[]{id + ""}, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return null;
        Category category = new Category();
        category.setId(cursor.getInt(cursor.getColumnIndex("id")));
        category.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        category.setDescription(cursor.getString(cursor.getColumnIndex("description")));
        cursor.close();
        return category;
    }

    public ArrayList<Category> getCategories() {
        ArrayList<Category> categoryArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_CATEGORIES, new String[]{"id", "title", "description"}, null, null, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return categoryArrayList;
        do {
            Category category = new Category();
            category.setId(cursor.getInt(cursor.getColumnIndex("id")));
            category.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            category.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            categoryArrayList.add(category);
        } while (cursor.moveToNext());
        cursor.close();
        return categoryArrayList;
    }

    public boolean setArticle(Article article) {
        Article item = getArticle(article.getId());
        if (null == item) {
            return addArticle(article);
        }
        return updateArticle(article);
    }

    private boolean addArticle(Article article) {
        ContentValues values = new ContentValues();
        values.put("id", article.getId());
        values.put("title", article.getTitle());
        values.put("summary", article.getSummary());
        values.put("category", article.getCategory());
        if (article.getContent() != null)
            values.put("content", article.getContent());
        long insertID = database.insert(DBHelper.TABLE_ARTICLES, null, values);
        return insertID > 0;
    }

    private boolean updateArticle(Article article) {
        ContentValues values = new ContentValues();
        values.put("title", article.getTitle());
        values.put("summary", article.getSummary());
        values.put("category", article.getCategory());
        if (article.getContent() != null)
            values.put("content", article.getContent());
        long insertID = database.update(DBHelper.TABLE_ARTICLES, values, "id = ?", new String[]{article.getId() + ""});
        return insertID > 0;
    }

    public Article getArticle(int id) {
        Cursor cursor = database.query(DBHelper.TABLE_ARTICLES, new String[]{"id", "summary", "title", "thumbnail", "category", "content"}, "id = ?", new String[]{id + ""}, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return null;
        Article article = new Article();
        article.setId(cursor.getInt(cursor.getColumnIndex("id")));
        article.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
        article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
        article.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
        article.setThumbnailURL(cursor.getString(cursor.getColumnIndex("thumbnail")));
        article.setContent(cursor.getString(cursor.getColumnIndex("content")));
        cursor.close();
        return article;
    }

    public ArrayList<Article> getArticles(int category) {
        ArrayList<Article> articleArrayList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_ARTICLES, new String[]{"id", "title", "summary", "category", "thumbnail"}, "category = ?", new String[]{category + ""}, null, null, null);
        if (null == cursor || !cursor.moveToFirst()) return articleArrayList;
        do {
            Article article = new Article();
            article.setId(cursor.getInt(cursor.getColumnIndex("id")));
            article.setCategory(cursor.getInt(cursor.getColumnIndex("category")));
            article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            article.setSummary(cursor.getString(cursor.getColumnIndex("summary")));
            article.setThumbnailURL(cursor.getString(cursor.getColumnIndex("thumbnail")));
            articleArrayList.add(article);
        } while (cursor.moveToNext());
        cursor.close();
        return articleArrayList;
    }
}
