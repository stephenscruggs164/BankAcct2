package com.company;
import java.util.*;
public class Main {

    public static void main(String args[]) {

        Scanner stdin = new Scanner(System.in);

        // Initializes BankAccount reference variable.
        BankAccount b1 = null;
        menu();
        int menu_choice = stdin.nextInt();

        // Loops till user wants to quit application
        while (menu_choice != 7) {

            // Processes user choice, note that by checking if the b1 refernce
            // variable is null, the user is only allowed to do options 2
            // through 7 if they have created a BankAccount object.
            if (menu_choice == 1) {

                if (b1 == null)
                    b1 = createAccount();
                else
                    System.out.println("Sorry you have already created an account.");
            }
            else if (b1 == null)
                System.out.println("You can not attempt any operations without creating an account first.");
            else if (menu_choice == 2)
                deposit(b1);
            else if (menu_choice == 3)
                withdraw(b1);
            else if (menu_choice == 4)
                changeRate(b1);
            else if (menu_choice == 5)
                b1.matureAccount();
            else if (menu_choice == 6)
                b1.printInfo();
            else if (menu_choice != 7)
                System.out.println("Invalid choice.");

            menu();
            menu_choice = stdin.nextInt();
        }
        System.out.println("Thank you for using the banking program.");
    }

    // Prints menu of possible choices to user.
    public static void menu() {

        System.out.println("Welcome to the Banking Program.");
        System.out.println("Enter your choice, remember you can only create an account once.");
        System.out.println("1. Create an Account.");
        System.out.println("2. Deposit money.");
        System.out.println("3. Write a check.");
        System.out.println("4. Change your account interestrate.");
        System.out.println("5. Mature account one year.");
        System.out.println("6. Print out account information.");
        System.out.println("7. Quit");
    }

    // Creates a BankAccount object based on information user enters, and
    // returns a reference to this object.
    public static BankAccount createAccount() {

        Scanner stdin = new Scanner(System.in);


        System.out.println("Enter your name.");
        String name = stdin.next();
        System.out.println("Enter your initial savings deposit.");
        double save = stdin.nextDouble();
        System.out.println("Enter your initial checking deposit.");
        double check = stdin.nextDouble();

        // Does not create account if information is invalid.
        if (save < 0 || check < 0)
            System.out.println("Sorry no account created, need non-negative values in savings and checking accounts.");
        else {
            System.out.println("You have a new account with a 5% interest rate.");
            BankAccount b = new BankAccount(name,save,check,0.05);
            return b;
        }
        return null;
    }

    // Asks user for deposit information and executes transaction if values
    // entered are valid.
    public static void deposit(BankAccount b) {

        Scanner stdin = new Scanner(System.in);

        System.out.println("Enter how much you would like to deposit.");
        double money = stdin.nextDouble();

        if (money > 0) {
            System.out.println("Which account, (S)avings or (C)hecking?");
            char ans = stdin.next().toUpperCase().charAt(0);

            if (ans == 'S')
                b.deposit_sav(money);
            else if (ans == 'C')
                b.deposit_chk(money);
            else
                System.out.println("Sorry, invalid choice.");
        }
        else
            System.out.println("Can not deposit negative money.");

    }

    // Asks user for withdraw amount and withdraws if sufficient funds are
    // in their Bank Account. Proper error message is printed if withdrawl is
    // not executed.
    public static void withdraw(BankAccount b) {

        Scanner stdin = new Scanner(System.in);

        System.out.println("Enter the amount to withdraw.");
        double money = stdin.nextDouble();

        if (money > 0) {
            if (b.noBounce(money))
                b.withdraw_chk(money);
            else
                System.out.println("Sorry, you do not have enough money in your checking account.");
        }
        else
            System.out.println("Sorry, you can not withdraw negative money.");

    }

    // Changes the interest rate of the Bank Account passed in as a parameter
    // as long as the user has sufficient funds to pay for the increased rate.
    public static void changeRate(BankAccount b) {

        Scanner stdin = new Scanner(System.in);

        System.out.println("Enter new interest rate.");
        System.out.println("Keep in mind that you will be charged $500 for each percentage point you raise your interest rate.");
        double rate =  stdin.nextDouble();

        if (rate <= b.getIrate())
            System.out.println("You can not decrease your interest rate.");
        else {
            double charge = 500 * (100 * (rate - b.getIrate()));
            if (b.noBounce(charge)) {
                b.changeRate(rate);
                b.withdraw_chk(charge);
            }
            else
                System.out.println("You do not have sufficient funds to raise your rate that much.");
        }

    }
}