package dinhhonganh.cnnt1.model;

import java.io.Serializable;

public class Book implements Serializable {
    private int book_id;
    private String book_name;
    private int book_page;
    private String book_image_path;
    private String book_price;
    private int author_id;
    private int publisher_id;
    private int type_of_book_id;
    private int store_id;
    private int stock_quantity;

    public Book(int book_id, String book_name, int book_page, String book_image_path, String book_price, int author_id, int publisher_id, int type_of_book_id, int store_id, int stock_quantity) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_page = book_page;
        this.book_image_path = book_image_path;
        this.book_price = book_price;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
        this.type_of_book_id = type_of_book_id;
        this.store_id = store_id;
        this.stock_quantity = stock_quantity;
    }
    public Book(int book_id, String book_name, int book_page, String book_image_path, String book_price, int author_id, int publisher_id, int type_of_book_id, int stock_quantity) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.book_page = book_page;
        this.book_image_path = book_image_path;
        this.book_price = book_price;
        this.author_id = author_id;
        this.publisher_id = publisher_id;
        this.type_of_book_id = type_of_book_id;
        this.stock_quantity = stock_quantity;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public int getBook_page() {
        return book_page;
    }

    public void setBook_page(int book_page) {
        this.book_page = book_page;
    }

    public String getBook_image_path() {
        return book_image_path;
    }

    public void setBook_image_path(String book_image_path) {
        this.book_image_path = book_image_path;
    }

    public String getBook_price() {
        return book_price;
    }

    public void setBook_price(String book_price) {
        this.book_price = book_price;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public void setPublisher_id(int publisher_id) {
        this.publisher_id = publisher_id;
    }

    public int getType_of_book_id() {
        return type_of_book_id;
    }

    public void setType_of_book_id(int type_of_book_id) {
        this.type_of_book_id = type_of_book_id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }
}
