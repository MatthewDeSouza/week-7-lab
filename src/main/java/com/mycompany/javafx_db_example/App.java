package com.mycompany.javafx_db_example;

import com.mycompany.javafx_db_example.db.ConnDbOps;
import com.mycompany.javafx_db_example.db.Person;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class App extends Application {

    private static Scene scene;
    private static ConnDbOps cdbop;

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        cdbop = new ConnDbOps();
        Scanner scan = new Scanner(System.in);

        char input;
        do {
            System.out.println(" ");
            System.out.println("============== Menu ==============");
            System.out.println("| To start GUI,           press 'g' |");
            System.out.println("| To connect to DB,       press 'c' |");
            System.out.println("| To display all users,   press 'a' |");
            System.out.println("| To insert to the DB,    press 'i' |");
            System.out.println("| To update a user,       press 'u' |");
            System.out.println("| To delete a user,       press 'd' |");
            System.out.println("| To query by name,       press 'q' |");
            System.out.println("| To exit,                press 'e' |");
            System.out.println("===================================");
            System.out.print("Enter your choice: ");
            input = scan.next().charAt(0);

            switch (input) {
                case 'g':
                    launch(args); //GUI
                    break;

                case 'c':
                    cdbop.connectToDatabase(); //Your existing method
                    break;

                case 'a':
                    cdbop.listAllUsers().forEach(System.out::println); //all users in DB
                    break;

                case 'i':
                    scan.nextLine();  // Consume leftover newline
                    System.out.print("Enter Name: ");
                    String name = scan.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scan.nextLine();
                    System.out.print("Enter Phone: ");
                    String phone = scan.nextLine();
                    System.out.print("Enter Address: ");
                    String address = scan.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scan.nextLine();
                    System.out.print("Enter Salary: ");
                    double salary = scan.nextDouble();
                    scan.nextLine();  // Consume leftover newline
                    Person newPerson = new Person(0, name, email, phone, address, password, salary);
                    cdbop.insertUser(newPerson);
                    break;

                case 'u':
                    System.out.print("Enter User ID to update: ");
                    int updateId = scan.nextInt();
                    scan.nextLine();  // Consume leftover newline
                    System.out.print("Enter New Name: ");
                    String updateName = scan.nextLine();
                    System.out.print("Enter New Email: ");
                    String updateEmail = scan.nextLine();
                    System.out.print("Enter New Phone: ");
                    String updatePhone = scan.nextLine();
                    System.out.print("Enter New Address: ");
                    String updateAddress = scan.nextLine();
                    System.out.print("Enter New Password: ");
                    String updatePassword = scan.nextLine();
                    System.out.print("Enter New Salary: ");
                    double updateSalary = scan.nextDouble();
                    scan.nextLine();  // Consume leftover newline
                    Person updatedPerson = new Person(updateId, updateName, updateEmail, updatePhone, updateAddress, updatePassword, updateSalary);
                    cdbop.updateUser(updatedPerson);
                    break;

                case 'd':
                    System.out.print("Enter User ID to delete: ");
                    int deleteId = scan.nextInt();
                    cdbop.deleteUser(deleteId);
                    break;

                case 'q':
                    scan.nextLine(); // Consume newline
                    System.out.print("Enter the name to query: ");
                    String queryName = scan.nextLine();
                    Person person = cdbop.queryUserByName(queryName); //Your queryUserByName method
                    if (person == null) {
                        System.out.printf("Error! Name '%s' not found in the database!", queryName);
                        break;
                    }
                    System.out.println(person);
                    break;

                case 'e':
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
            System.out.println(" ");
        } while (input != 'e');

        scan.close();
    }

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }
}
