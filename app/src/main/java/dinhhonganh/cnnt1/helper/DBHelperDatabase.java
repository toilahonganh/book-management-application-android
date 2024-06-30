package dinhhonganh.cnnt1.helper;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;


import java.util.ArrayList;
import java.util.List;

import dinhhonganh.cnnt1.model.Author;
import dinhhonganh.cnnt1.model.Book;
import dinhhonganh.cnnt1.model.Category;
import dinhhonganh.cnnt1.model.Order;
import dinhhonganh.cnnt1.model.Publisher;
import dinhhonganh.cnnt1.model.Store;

public class DBHelperDatabase extends SQLiteOpenHelper {
    private static final String TAG = "DBHelperDatabase";
    public static final String DATABASE_NAME = "cosodulieu.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_BOOK = "Book";
    public static final String COLUMN_BOOK_ID = "book_id";
    public static final String COLUMN_BOOK_NAME = "book_name";
    public static final String COLUMN_BOOK_IMAGE_URL = "book_image_url";
    public static final String COLUMN_BOOK_PAGE = "book_page";
    public static final String COLUMN_BOOK_IMAGE = "book_image";
    public static final String COLUMN_BOOK_PRICE = "book_price";
    public static final String COLUMN_AUTHOR_ID_FK = "author_id";
    public static final String COLUMN_PUBLISHER_ID_FK = "publisher_id";
    public static final String COLUMN_TYPE_OF_BOOK_ID_FK = "type_of_book_id";
    public static final String COLUMN_STORE_ID_FK = "store_id";
    public static final String COLUMN_STOCK_QUANTITY = "stock_quantity";

    public static final String TABLE_AUTHOR = "Author";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_AUTHOR_NAME = "author_name";

    public static final String TABLE_PUBLISHER = "Publisher";
    public static final String COLUMN_PUBLISHER_ID = "publisher_id";
    public static final String COLUMN_PUBLISHER_NAME = "publisher_name";

    public static final String TABLE_TYPE_OF_BOOK = "Type";
    public static final String COLUMN_TYPE_OF_BOOK_ID = "type_of_book_id";
    public static final String COLUMN_TYPE_OF_BOOK_NAME = "type_of_book_name";

    public static final String TABLE_STORE = "Store";
    public static final String COLUMN_STORE_ID = "store_id";
    public static final String COLUMN_STORE_NAME = "store_name";
    public static final String COLUMN_STORE_ADDRESS = "store_address";

    public static final String TABLE_ORDER = "Orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_USER_ID_FK = "user_id";
    public static final String COLUMN_BOOK_ID_FK = "book_id";
    public static final String COLUMN_QUANTITY = "quantity";

    public static final String TABLE_USER = "User";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USER_GMAIL = "user_gmail";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "user_password";
    public static final String COLUMN_USER_BIRTHDAY = "user_birthday";
    public static final String COLUMN_USER_ADDRESS = "user_address";
    public static final String COLUMN_USER_GENDER = "user_gender";

    public static final String COLUMN_USER_PHONE_NUMBER = "user_phone_number";

    public DBHelperDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAuthorTable = "CREATE TABLE " + TABLE_AUTHOR + "(" +
                COLUMN_AUTHOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_AUTHOR_NAME + " TEXT NOT NULL" +
                ");";

        String createPublisherTable = "CREATE TABLE " + TABLE_PUBLISHER + "(" +
                COLUMN_PUBLISHER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PUBLISHER_NAME + " TEXT NOT NULL" +
                ");";

        String createTypeOfBookTable = "CREATE TABLE " + TABLE_TYPE_OF_BOOK + "(" +
                COLUMN_TYPE_OF_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TYPE_OF_BOOK_NAME + " TEXT NOT NULL" +
                ");";

        String createStoreTable = "CREATE TABLE " + TABLE_STORE + "(" +
                COLUMN_STORE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STORE_NAME + " TEXT NOT NULL, " +
                COLUMN_STORE_ADDRESS + " TEXT NOT NULL" +
                ");";

        String createUserTable = "CREATE TABLE " + TABLE_USER + "(" +
                COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_GMAIL + " TEXT NOT NULL, " +
                COLUMN_USER_NAME + " TEXT NOT NULL, " +
                COLUMN_USER_PASSWORD + " TEXT NOT NULL, " +
                COLUMN_USER_BIRTHDAY + " TEXT NOT NULL, " +
                COLUMN_USER_GENDER + " TEXT NOT NULL, " +
                COLUMN_USER_ADDRESS + " TEXT NOT NULL, " +
                COLUMN_USER_PHONE_NUMBER + " TEXT NOT NULL" +

                ");";

        String createBookTable = "CREATE TABLE " + TABLE_BOOK + "(" +
                COLUMN_BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_BOOK_NAME + " TEXT NOT NULL, " +
                COLUMN_BOOK_PAGE + " INTEGER NOT NULL, " +
                COLUMN_BOOK_IMAGE_URL + " TEXT, " + // Thêm cột mới cho đường dẫn ảnh
                COLUMN_BOOK_PRICE + " TEXT NOT NULL, " +
                COLUMN_AUTHOR_ID_FK + " INTEGER NOT NULL, " +
                COLUMN_PUBLISHER_ID_FK + " INTEGER NOT NULL, " +
                COLUMN_TYPE_OF_BOOK_ID_FK + " INTEGER NOT NULL, " +
                COLUMN_STORE_ID_FK + " INTEGER NOT NULL, " +
                COLUMN_STOCK_QUANTITY + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_AUTHOR_ID_FK + ") REFERENCES " + TABLE_AUTHOR + "(" + COLUMN_AUTHOR_ID + "), " +
                "FOREIGN KEY(" + COLUMN_PUBLISHER_ID_FK + ") REFERENCES " + TABLE_PUBLISHER + "(" + COLUMN_PUBLISHER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_TYPE_OF_BOOK_ID_FK + ") REFERENCES " + TABLE_TYPE_OF_BOOK + "(" + COLUMN_TYPE_OF_BOOK_ID + "), " +
                "FOREIGN KEY(" + COLUMN_STORE_ID_FK + ") REFERENCES " + TABLE_STORE + "(" + COLUMN_STORE_ID + ")" +
                ");";


        String createOrderTable = "CREATE TABLE " + TABLE_ORDER + "(" +
                COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USER_ID_FK + " INTEGER NOT NULL, " +
                COLUMN_BOOK_ID_FK + " INTEGER NOT NULL, " +
                COLUMN_QUANTITY + " INTEGER NOT NULL, " +
                "FOREIGN KEY(" + COLUMN_USER_ID_FK + ") REFERENCES " + TABLE_USER + "(" + COLUMN_USER_ID + "), " +
                "FOREIGN KEY(" + COLUMN_BOOK_ID_FK + ") REFERENCES " + TABLE_BOOK + "(" + COLUMN_BOOK_ID + ")" +
                ");";

        Log.d("DBHelper", "Database created successfully");
        db.execSQL(createAuthorTable);
        db.execSQL(createPublisherTable);
        db.execSQL(createTypeOfBookTable);
        db.execSQL(createStoreTable);
        db.execSQL(createUserTable);
        db.execSQL(createBookTable);
        db.execSQL(createOrderTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTHOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUBLISHER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE_OF_BOOK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);

        onCreate(db);
    }

    public boolean addAuthor(String authorName) {
        return addRecord(TABLE_AUTHOR, COLUMN_AUTHOR_NAME, authorName);
    }

    public boolean addPublisher(String publisherName) {
        return addRecord(TABLE_PUBLISHER, COLUMN_PUBLISHER_NAME, publisherName);
    }

    public boolean addTypeOfBook(String typeOfBookName) {
        return addRecord(TABLE_TYPE_OF_BOOK, COLUMN_TYPE_OF_BOOK_NAME, typeOfBookName);
    }

    public boolean addStore(String storeName, String storeAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STORE_NAME, storeName);
        values.put(COLUMN_STORE_ADDRESS, storeAddress);
        long result = db.insert(TABLE_STORE, null, values);
        db.close();
        return result != -1;
    }

    public boolean addUser(String gmail, String name, String password, String birthday, String gender, String address, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_GMAIL, gmail);
        values.put(COLUMN_USER_NAME, name);
        values.put(COLUMN_USER_PASSWORD, password);
        values.put(COLUMN_USER_BIRTHDAY, birthday);
        values.put(COLUMN_USER_GENDER, gender);
        values.put(COLUMN_USER_ADDRESS, address);
        values.put(COLUMN_USER_PHONE_NUMBER, phoneNumber);
        long result = db.insert(TABLE_USER, null, values);
        db.close();
        return result != -1;
    }

    public boolean addBook(String tensach, int sotrang, String anhbiaURL, String giasach, int authorId, int publisherId, int typeOfBookId, int storeId, int soluong) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_NAME, tensach);
        values.put(COLUMN_BOOK_PAGE, sotrang);
        values.put(COLUMN_BOOK_IMAGE_URL, anhbiaURL); // Lưu tên ảnh hoặc đường dẫn ảnh vào cơ sở dữ liệu
        values.put(COLUMN_BOOK_PRICE, giasach);
        values.put(COLUMN_AUTHOR_ID_FK, authorId);
        values.put(COLUMN_PUBLISHER_ID_FK, publisherId);
        values.put(COLUMN_TYPE_OF_BOOK_ID_FK, typeOfBookId);
        values.put(COLUMN_STORE_ID_FK, storeId);
        values.put(COLUMN_STOCK_QUANTITY, soluong);

        long result = db.insert(TABLE_BOOK, null, values);
        return result != -1;
    }


    public boolean addOrder(int userId, int bookId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID_FK, userId);
        values.put(COLUMN_BOOK_ID_FK, bookId);
        values.put(COLUMN_QUANTITY, quantity);
        long result = db.insert(TABLE_ORDER, null, values);
        db.close();
        return result != -1;
    }

    private boolean addRecord(String tableName, String columnName, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(columnName, value);
        long result = db.insert(tableName, null, values);
        db.close();
        return result != -1;
    }

    public boolean isAuthorExists(String authorName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_AUTHOR + " WHERE " + COLUMN_AUTHOR_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{authorName});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }
    public boolean isTypeOfBookExists(String typeOfBookName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_TYPE_OF_BOOK + " WHERE " + COLUMN_TYPE_OF_BOOK_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{typeOfBookName});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }

    public boolean isStoreExists(String storeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_STORE + " WHERE " + COLUMN_STORE_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{storeName});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }
    public boolean isPublisherExists(String publisherName) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT 1 FROM " + TABLE_PUBLISHER + " WHERE " + COLUMN_PUBLISHER_NAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{publisherName});

        boolean exists = cursor.moveToFirst();
        cursor.close();
        db.close();

        return exists;
    }
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_GMAIL + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean userExists = cursor.moveToFirst();

        cursor.close();
        db.close();

        return userExists;
    }

    public List<String> getAuthors() {
        List<String> authors = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_AUTHOR_NAME + " FROM " + TABLE_AUTHOR;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                authors.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return authors;
    }

    public List<String> getPublishers() {
        List<String> publishers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_PUBLISHER_NAME + " FROM " + TABLE_PUBLISHER;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                publishers.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHER_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return publishers;
    }

    public List<String> getTypesOfBooks() {
        List<String> typesOfBooks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_TYPE_OF_BOOK_NAME + " FROM " + TABLE_TYPE_OF_BOOK;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                typesOfBooks.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE_OF_BOOK_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return typesOfBooks;
    }

    public List<String> getStores() {
        List<String> stores = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_STORE_NAME + " FROM " + TABLE_STORE;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                stores.add(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STORE_NAME)));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return stores;
    }

    public Cursor getAuthorsCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_AUTHOR_NAME}; // Chỉ cần cột tên tác giả
        Cursor cursor = db.query(TABLE_AUTHOR, projection, null, null, null, null, null);
        return cursor;
    }

    public Cursor getBooksCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {
                COLUMN_BOOK_ID,
                COLUMN_BOOK_NAME,
                COLUMN_BOOK_PAGE,
                COLUMN_BOOK_IMAGE,
                COLUMN_BOOK_PRICE,
                COLUMN_AUTHOR_ID_FK,
                COLUMN_PUBLISHER_ID_FK,
                COLUMN_TYPE_OF_BOOK_ID_FK,
                COLUMN_STORE_ID_FK,
                COLUMN_STOCK_QUANTITY
        };
        Cursor cursor = db.query(TABLE_BOOK, projection, null, null, null, null, null);
        return cursor;
    }
    public String getAuthorNameById(int authorId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_AUTHOR_NAME};
        String selection = COLUMN_AUTHOR_ID + " = ?";
        String[] selectionArgs = {String.valueOf(authorId)};
        Cursor cursor = db.query(TABLE_AUTHOR, projection, selection, selectionArgs, null, null, null);
        String authorName = null;
        if (cursor != null && cursor.moveToFirst()) {
            authorName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_NAME));
            cursor.close();
        }
        db.close();
        return authorName;
    }

    public String getPublisherNameById(int publisherId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_PUBLISHER_NAME};
        String selection = COLUMN_PUBLISHER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(publisherId)};
        Cursor cursor = db.query(TABLE_PUBLISHER, projection, selection, selectionArgs, null, null, null);
        String publisherName = null;
        if (cursor != null && cursor.moveToFirst()) {
            publisherName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHER_NAME));
            cursor.close();
        }
        db.close();
        return publisherName;
    }

    public String getTypeOfBookNameById(int typeOfBookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_TYPE_OF_BOOK_NAME};
        String selection = COLUMN_TYPE_OF_BOOK_ID + " = ?";
        String[] selectionArgs = {String.valueOf(typeOfBookId)};
        Cursor cursor = db.query(TABLE_TYPE_OF_BOOK, projection, selection, selectionArgs, null, null, null);
        String typeOfBookName = null;
        if (cursor != null && cursor.moveToFirst()) {
            typeOfBookName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE_OF_BOOK_NAME));
            cursor.close();
        }
        db.close();
        return typeOfBookName;
    }


    public String getStoreNameById(int storeId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] projection = {COLUMN_STORE_NAME};
        String selection = COLUMN_STORE_ID + " = ?";
        String[] selectionArgs = {String.valueOf(storeId)};
        Cursor cursor = db.query(TABLE_STORE, projection, selection, selectionArgs, null, null, null);
        String storeName = null;
        if (cursor != null && cursor.moveToFirst()) {
            storeName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STORE_NAME));
            cursor.close();
        }
        db.close();
        return storeName;
    }


    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String queryString = "SELECT " + COLUMN_BOOK_ID + ", " + COLUMN_BOOK_NAME + ", " + COLUMN_BOOK_PAGE + ", " + COLUMN_BOOK_IMAGE_URL + ", " + COLUMN_BOOK_PRICE + ", " + COLUMN_AUTHOR_ID_FK + ", " + COLUMN_PUBLISHER_ID_FK + ", " + COLUMN_TYPE_OF_BOOK_ID_FK + ", " + COLUMN_STORE_ID_FK + ", " + COLUMN_STOCK_QUANTITY + " FROM " + TABLE_BOOK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int book_id = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID));
                @SuppressLint("Range") String book_name = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
                @SuppressLint("Range") int book_page = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_PAGE));
                @SuppressLint("Range") String book_image_url = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_URL)); // Lấy tên ảnh hoặc đường dẫn ảnh
                @SuppressLint("Range") String book_price = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PRICE));
                @SuppressLint("Range") int author_id = cursor.getInt(cursor.getColumnIndex(COLUMN_AUTHOR_ID_FK));
                @SuppressLint("Range") int publisher_id = cursor.getInt(cursor.getColumnIndex(COLUMN_PUBLISHER_ID_FK));
                @SuppressLint("Range") int type_of_book_id = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_OF_BOOK_ID_FK));
                @SuppressLint("Range") int store_id = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_ID_FK));
                @SuppressLint("Range") int stock_quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_STOCK_QUANTITY));

                Book book = new Book(book_id, book_name, book_page, book_image_url, book_price, author_id, publisher_id, type_of_book_id, store_id, stock_quantity);
                books.add(book);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getAllBooks: No data found");
        }

        cursor.close();
        db.close();
        return books;
    }
    public int getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USER, new String[]{COLUMN_USER_ID},
                COLUMN_USER_GMAIL + " = ?", new String[]{email}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int userId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID));
            cursor.close();
            return userId;
        }
        if (cursor != null) {
            cursor.close();
        }
        return -1;
    }

    public Cursor getUserData(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_USER, null, COLUMN_USER_ID + " = ?", new String[]{String.valueOf(userId)},
                null, null, null);
    }

    public SQLiteDatabase ketNoiDBRead() {
        SQLiteDatabase db = getReadableDatabase();
        return db;
    }
    public SQLiteDatabase ketNoiDBWrite() {
        SQLiteDatabase db = getWritableDatabase();
        return db;
    }
    public List<Book> getBooksByTypeOfBookId(int typeOfBookId) {
        List<Book> books = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_BOOK_ID,
                COLUMN_BOOK_NAME,
                COLUMN_BOOK_PAGE,
                COLUMN_BOOK_IMAGE_URL,
                COLUMN_BOOK_PRICE,
                COLUMN_AUTHOR_ID_FK,
                COLUMN_PUBLISHER_ID_FK,
                COLUMN_TYPE_OF_BOOK_ID_FK,
                COLUMN_STORE_ID_FK,
                COLUMN_STOCK_QUANTITY
        };

        String selection = COLUMN_TYPE_OF_BOOK_ID_FK + " = ?";
        String[] selectionArgs = {String.valueOf(typeOfBookId)};

        Cursor cursor = db.query(TABLE_BOOK, projection, selection, selectionArgs, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int book_id = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID));
                @SuppressLint("Range") String book_name = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_NAME));
                @SuppressLint("Range") int book_page = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_PAGE));
                @SuppressLint("Range") String book_image_url = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_IMAGE_URL));
                @SuppressLint("Range") String book_price = cursor.getString(cursor.getColumnIndex(COLUMN_BOOK_PRICE));
                @SuppressLint("Range") int author_id = cursor.getInt(cursor.getColumnIndex(COLUMN_AUTHOR_ID_FK));
                @SuppressLint("Range") int publisher_id = cursor.getInt(cursor.getColumnIndex(COLUMN_PUBLISHER_ID_FK));
                @SuppressLint("Range") int type_of_book_id = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_OF_BOOK_ID_FK));
                @SuppressLint("Range") int store_id = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_ID_FK));
                @SuppressLint("Range") int stock_quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_STOCK_QUANTITY));

                Book book = new Book(book_id, book_name, book_page, book_image_url, book_price, author_id, publisher_id, type_of_book_id, store_id, stock_quantity);
                books.add(book);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getBooksByTypeOfBookId: No data found");
        }

        cursor.close();
        db.close();

        return books;
    }

    public List<Book> getBooksByType(int typeOfBookId) {
        return getBooksByTypeOfBookId(typeOfBookId);
    }
    public List<Author> getAllAuthors() {
        List<Author> authors = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_AUTHOR_ID,
                COLUMN_AUTHOR_NAME
        };

        Cursor cursor = db.query(TABLE_AUTHOR, projection, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int authorId = cursor.getInt(cursor.getColumnIndex(COLUMN_AUTHOR_ID));
                @SuppressLint("Range") String authorName = cursor.getString(cursor.getColumnIndex(COLUMN_AUTHOR_NAME));

                Author author = new Author(authorId, authorName);
                authors.add(author);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getAllAuthors: No data found");
        }

        cursor.close();
        db.close();

        return authors;
    }

    public List<Store> getAllStores() {
        List<Store> stores = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_STORE_ID,
                COLUMN_STORE_NAME,
                COLUMN_STORE_ADDRESS
        };

        Cursor cursor = db.query(TABLE_STORE, projection, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int storeId = cursor.getInt(cursor.getColumnIndex(COLUMN_STORE_ID));
                @SuppressLint("Range") String storeName = cursor.getString(cursor.getColumnIndex(COLUMN_STORE_NAME));
                @SuppressLint("Range") String storeAddress = cursor.getString(cursor.getColumnIndex(COLUMN_STORE_ADDRESS));


                Store store = new Store(storeId, storeName, storeAddress);
                stores.add(store);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getAllStores: No data found");
        }

        cursor.close();
        db.close();

        return stores;
    }

    public List<Publisher> getAllPublisher() {
        List<Publisher> stores = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_PUBLISHER_ID,
                COLUMN_PUBLISHER_NAME
        };

        Cursor cursor = db.query(TABLE_PUBLISHER, projection, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int storeId = cursor.getInt(cursor.getColumnIndex(COLUMN_PUBLISHER_ID));
                @SuppressLint("Range") String storeName = cursor.getString(cursor.getColumnIndex(COLUMN_PUBLISHER_NAME));


                Publisher store = new Publisher(storeId, storeName);
                stores.add(store);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getAllPublisher: No data found");
        }

        cursor.close();
        db.close();

        return stores;
    }

    public List<Category> getAllCates() {
        List<Category> stores = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_TYPE_OF_BOOK_ID,
                COLUMN_TYPE_OF_BOOK_NAME
        };

        Cursor cursor = db.query(TABLE_TYPE_OF_BOOK, projection, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int storeId = cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE_OF_BOOK_ID));
                @SuppressLint("Range") String storeName = cursor.getString(cursor.getColumnIndex(COLUMN_TYPE_OF_BOOK_NAME));


                Category store = new Category(storeId, storeName);
                stores.add(store);
            } while (cursor.moveToNext());
        } else {
            Log.d(TAG, "getAllCates: No data found");
        }

        cursor.close();
        db.close();

        return stores;
    }

    public boolean deleteAuthorById(int authorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_AUTHOR, COLUMN_AUTHOR_ID + " = ?", new String[]{String.valueOf(authorId)}) > 0;
    }

    public boolean deleteStore(int storeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STORE, COLUMN_STORE_ID + " = ?", new String[]{String.valueOf(storeId)}) > 0;
    }

    public boolean deleteBook(int bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_BOOK, COLUMN_BOOK_ID + " = ?", new String[]{String.valueOf(bookId)}) > 0;
    }
    public boolean deletePublisher(int publisherId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_PUBLISHER, COLUMN_PUBLISHER_ID + " = ?", new String[]{String.valueOf(publisherId)}) > 0;
    }

    public boolean deleteAuthor(int authorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_AUTHOR, COLUMN_AUTHOR_ID + " = ?", new String[]{String.valueOf(authorId)}) > 0;
    }

    public boolean deleteCates(int catesId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TYPE_OF_BOOK, COLUMN_TYPE_OF_BOOK_ID + " = ?", new String[]{String.valueOf(catesId)}) > 0;
    }

    public boolean deleteOrder(int orderId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_ORDER, COLUMN_ORDER_ID + " = ?", new String[]{String.valueOf(orderId)}) > 0;
    }


    public boolean updateBook(int bookId, String bookName, String bookImageUrl, int bookPage, String bookPrice, int authorId, int publisherId, int typeOfBookId, int storeId, int stockQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BOOK_NAME, bookName);
        values.put(COLUMN_BOOK_IMAGE_URL, bookImageUrl);
        values.put(COLUMN_BOOK_PAGE, bookPage);
        values.put(COLUMN_BOOK_PRICE, bookPrice);
        values.put(COLUMN_AUTHOR_ID_FK, authorId);
        values.put(COLUMN_PUBLISHER_ID_FK, publisherId);
        values.put(COLUMN_TYPE_OF_BOOK_ID_FK, typeOfBookId);
        values.put(COLUMN_STORE_ID_FK, storeId);
        values.put(COLUMN_STOCK_QUANTITY, stockQuantity);

        String selection = COLUMN_BOOK_ID + " = ?";
        String[] selectionArgs = {String.valueOf(bookId)};

        int rowsUpdated = db.update(TABLE_BOOK, values, selection, selectionArgs);
        db.close();

        return rowsUpdated > 0;
    }

    public boolean updateUser(int userId, String userName, String userGmail, String userBirthday, String userGender, String userAddress, String userPhone, String userPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_USER_NAME, userName);
        values.put(COLUMN_USER_GMAIL, userGmail);
        values.put(COLUMN_USER_BIRTHDAY, userBirthday);
        values.put(COLUMN_USER_PHONE_NUMBER, userPhone);
        values.put(COLUMN_USER_GENDER, userGender);
        values.put(COLUMN_USER_ADDRESS, userAddress);
        values.put(COLUMN_USER_PASSWORD, userPassword);

        String selection = COLUMN_USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userId)};

        int rowsUpdated = db.update(TABLE_USER, values, selection, selectionArgs);
        db.close();

        return rowsUpdated > 0;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Select all rows from the Orders table
            String query = "SELECT * FROM " + TABLE_ORDER;
            cursor = db.rawQuery(query, null);

            // Iterate through the cursor to retrieve each order
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve order details from the cursor
                    @SuppressLint("Range") int orderId = cursor.getInt(cursor.getColumnIndex(COLUMN_ORDER_ID));
                    @SuppressLint("Range") int userId = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID_FK));
                    @SuppressLint("Range") int bookId = cursor.getInt(cursor.getColumnIndex(COLUMN_BOOK_ID_FK));
                    @SuppressLint("Range") int quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));

                    // Create Order object and add it to the list
                    Order order = new Order(orderId, userId, bookId, quantity);
                    orders.add(order);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving orders: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return orders;
    }

    public Book getBookById(int bookId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        Book book = null;

        try {
            String[] projection = {
                    COLUMN_BOOK_ID,
                    COLUMN_BOOK_NAME,
                    COLUMN_BOOK_PAGE,
                    COLUMN_BOOK_IMAGE_URL,
                    COLUMN_BOOK_PRICE,
                    COLUMN_AUTHOR_ID_FK,
                    COLUMN_PUBLISHER_ID_FK,
                    COLUMN_TYPE_OF_BOOK_ID_FK,
                    COLUMN_STORE_ID_FK,
                    COLUMN_STOCK_QUANTITY
            };

            String selection = COLUMN_BOOK_ID + " = ?";
            String[] selectionArgs = { String.valueOf(bookId) };

            cursor = db.query(
                    TABLE_BOOK,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_NAME));
                int pages = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PAGE));
                String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMAGE_URL));
                String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PRICE));
                int authorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_ID_FK));
                int publisherId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHER_ID_FK));
                int typeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE_OF_BOOK_ID_FK));
                int storeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STORE_ID_FK));
                int stockQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK_QUANTITY));

                // Tạo đối tượng Book từ dữ liệu từ cơ sở dữ liệu
                book = new Book(id, name, pages, imageUrl, price, authorId, publisherId, typeId, storeId, stockQuantity);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving book by id: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return book;
    }



    public List<Book> getBooksInCart(int userId) {
        List<Book> booksInCart = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            // Select books from the Orders table for the given user id
            String query = "SELECT * FROM " + TABLE_ORDER + " WHERE " + COLUMN_USER_ID_FK + "=?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(userId)});

            // Iterate through the cursor to retrieve each book in the cart
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Retrieve book id from the cursor
                    int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID_FK));

                    // Get book details by book id
                    Book book = getBookById(bookId);
                    if (book != null) {
                        booksInCart.add(book);
                    }
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving books in cart: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return booksInCart;
    }
    public List<Book> getBooksByName(String bookName) {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String[] projection = {
                    COLUMN_BOOK_ID,
                    COLUMN_BOOK_NAME,
                    COLUMN_BOOK_PAGE,
                    COLUMN_BOOK_IMAGE_URL,
                    COLUMN_BOOK_PRICE,
                    COLUMN_AUTHOR_ID_FK,
                    COLUMN_PUBLISHER_ID_FK,
                    COLUMN_TYPE_OF_BOOK_ID_FK,
                    COLUMN_STORE_ID_FK,
                    COLUMN_STOCK_QUANTITY
            };

            String selection = COLUMN_BOOK_NAME + " LIKE ?";
            String[] selectionArgs = new String[]{"%" + bookName + "%"};

            cursor = db.query(
                    TABLE_BOOK,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_NAME));
                    int pages = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PAGE));
                    String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_IMAGE_URL));
                    String price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BOOK_PRICE));
                    int authorId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_ID_FK));
                    int publisherId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_PUBLISHER_ID_FK));
                    int typeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE_OF_BOOK_ID_FK));
                    int storeId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STORE_ID_FK));
                    int stockQuantity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STOCK_QUANTITY));

                    Book book = new Book(id, name, pages, imageUrl, price, authorId, publisherId, typeId, storeId, stockQuantity);
                    bookList.add(book);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving books by name: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return bookList;
    }




}