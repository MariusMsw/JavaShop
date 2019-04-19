import controller.ProductsController;
import data.Product;
import data.Products;
import views.ProductsJTable;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        Products products = Products.getInstance();

     //   products.printAllProducts();

        ProductsJTable view = new ProductsJTable();

        ProductsController controller = new ProductsController(view,products);

    }

}
