import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Registration extends JFrame{
    private JButton submitBtn;
    private JTextField name;
    private JTextField username;
    private JTextField email;
    private JTextField phone;
    private JTextField address;
    private JButton resetBtn;
    private JButton closeBtn;
    private JPasswordField password;
    private JPasswordField conPassword;
    private JPanel mainPanel;

    public Registration() {
        resetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                name.setText("");
                username.setText("");
                password.setText("");
                conPassword.setText("");
                email.setText("");
                phone.setText("");
                address.setText("");
            }
        });
        closeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });
        submitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //Check if passwords match
                String pWord = String.valueOf(password.getPassword());
                String conWord = String.valueOf(conPassword.getPassword());
                if (!pWord.equals(conWord))
                {
                    JOptionPane.showMessageDialog(null, "The passwords don't match");
                }
                else
                {
                    try
                    {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/register", "root", "****");
                        String sql = "INSERT INTO registration(Name, Username, Password, Email, Phone, Address) VALUES(?,?,?,?,?,?)";
                        PreparedStatement pr = con.prepareStatement(sql);
                        pr.setString(1, name.getText());
                        pr.setString(2, username.getText());
                        pr.setString(3, pWord);
                        pr.setString(4, email.getText());
                        pr.setString(5, phone.getText());
                        pr.setString(6, address.getText());
                        int count = pr.executeUpdate();
                        if (count == 1)
                        {
                            JOptionPane.showMessageDialog(null, "Record added successfully");
                            con.close();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null, "No record added");
                            con.close();
                        }
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        Registration r = new Registration();
        r.setContentPane(r.mainPanel);
        int width = 400, height = 300;
        int x = (1368 - width) / 2;
        int y = (768 - height) / 2;
        r.setSize(width, height);
        r.setBounds(x, y, width, height);
        r.setVisible(true);
        r.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
