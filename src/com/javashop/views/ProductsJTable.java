package views;

import data.Product;
import data.Products;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ProductsJTable {

    private Products products;
    private JFrame mainFrame;

    // !! TO DO:
    //  -mode data fetching to controller
    //
    public ProductsJTable(){

        products = Products.getInstance();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Creating the main frame which displays
        // products' table and login button
        mainFrame = new JFrame("Shop");
        mainFrame.setSize((int) screenSize.getWidth() / 2,
                          (int) screenSize.getHeight() / 2);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());


        ArrayList<Product> theProducts = Products.getAllProducts();

        String[][] data = null;
        if(theProducts != null) {
            data = new String[theProducts.size()][3];
        }else {
            data = new String[0][0];
        }

        int index = 0;
        for (Product product : theProducts) {

            data[index][0] = product.getName();
            data[index][1] = String.valueOf(product.getPrice());
            data[index][2] = String.valueOf(product.getQuantity());

            index++;
        }

        String[] columnNames = {"Name", "Price", "Stock"};


        JTable table = new JTable(data, columnNames);
        table.setBounds(30, 40, 200, 300);

        JScrollPane scrollPane = new JScrollPane(table);

        mainFrame.add(scrollPane,BorderLayout.CENTER);
        mainFrame.setVisible(true);

    }

    public void setLoginButtonActionListener(ActionListener listener){

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(listener);
        mainFrame.add(loginButton,BorderLayout.SOUTH);

    }

    public JFrame getMainFrame(){
        return mainFrame;
    }

}
