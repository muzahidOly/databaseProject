import java.util.Date;

public class quote {
    private String email;
    private String tree_price;
    private String tree_size;
    private String tree_height;
    private Date quote_date;
    private String quote_response;

    public quote() {
    }

    public quote(String email, String tree_price, String tree_size, String tree_height, Date quote_date, String quote_response) {
        this.email = email;
        this.tree_price = tree_price;
        this.tree_size = tree_size;
        this.tree_height = tree_height;
        this.quote_date = quote_date;
        this.quote_response = quote_response;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTreePrice() {
        return tree_price;
    }

    public void setTreePrice(String tree_price) {
        this.tree_price = tree_price;
    }

    public String getTreeSize() {
        return tree_size;
    }

    public void setTreeSize(String tree_size) {
        this.tree_size = tree_size;
    }

    public String getTreeHeight() {
        return tree_height;
    }

    public void setTreeHeight(String tree_height) {
        this.tree_height = tree_height;
    }

    public Date getQuoteDate() {
        return quote_date;
    }

    public void setQuoteDate(Date quote_date) {
        this.quote_date = quote_date;
    }

    public String getQuoteResponse() {
        return quote_response;
    }

    public void setQuoteResponse(String quote_response) {
        this.quote_response = quote_response;
    }
}