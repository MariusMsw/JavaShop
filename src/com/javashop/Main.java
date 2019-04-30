package com.javashop;

import com.javashop.controller.ProductsController;
import com.javashop.data.Products;
import com.javashop.views.ProductsJTable;

public class Main {

    public static void main(String[] args) {

        Products products = Products.getInstance();

     //   products.printAllProducts();

        ProductsJTable view = new ProductsJTable();

        ProductsController controller = new ProductsController(view,products);
    }

}
