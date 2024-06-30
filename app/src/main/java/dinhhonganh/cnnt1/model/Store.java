package dinhhonganh.cnnt1.model;

public class Store {
    String store_name, store_address;
    int store_id;

    public Store(int store_id, String store_name, String store_address) {
        this.store_id = store_id;
        this.store_name = store_name;
        this.store_address = store_address;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getStore_address() {
        return store_address;
    }

    public void setStore_address(String store_address) {
        this.store_address = store_address;
    }
}
