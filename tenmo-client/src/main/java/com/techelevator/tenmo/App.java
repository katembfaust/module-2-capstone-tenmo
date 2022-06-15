package com.techelevator.tenmo;

import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.*;
import io.cucumber.java.bs.A;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;
    private AccountServiceREST accountServiceREST;
    private TransferServiceREST transferServiceREST;
    private UserService userService;
    private TransferStatusREST transferStatusREST;
    private TransferTypeREST transferTypeREST;


    public App() {
    }


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }
    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

	private void viewCurrentBalance() {
        accountServiceREST = new AccountServiceREST(API_BASE_URL, currentUser);
        Double balance = accountServiceREST.getBalance();
        System.out.println("Your current account balance is: $ " + balance);
	}

	private void viewTransferHistory() {
        transferServiceREST = new TransferServiceREST(API_BASE_URL, currentUser);
        transferTypeREST = new TransferTypeREST(API_BASE_URL, currentUser);
        transferStatusREST = new TransferStatusREST(API_BASE_URL, currentUser);
        userService = new UserService(API_BASE_URL, currentUser);
        currentUser = new AuthenticatedUser();
//       User transferUser = userService.getByUserId(currentUser.getUser().getId());
//        Long userId = transferUser.getId();
        Transfer[] userTransfers = transferServiceREST.getTransfersByUserId(1001L);
        System.out.println("-------------------------------------------\n" +
                "Transfer ID     " + "Recipient ID      " + "Amount             \n" +
                "-------------------------------------------\n" );
       for (Transfer transferList : userTransfers) {
//           if (!transferList.getTransferId().equals(null)) {
              System.out.println(transferList.getTransferId() + "            "+ transferList.getAccountTo() + "                " + transferList.getAmount());
           }
//        transferTypeREST.getTransferTypeById(currentUser, transferList.getTransferId())
//        transferStatusREST.getTransferStatusById(currentUser, transferList.getTransferId())
       }

	private void viewPendingRequests() {
		// TODO Auto-generated method stub
		
	}

	private void sendBucks() {
        accountServiceREST = new AccountServiceREST(API_BASE_URL, currentUser);
        userService = new UserService(API_BASE_URL, currentUser);
        transferServiceREST = new TransferServiceREST(API_BASE_URL, currentUser);
        transferStatusREST = new TransferStatusREST(API_BASE_URL, currentUser);
        transferTypeREST = new TransferTypeREST(API_BASE_URL, currentUser);

        Double balance = accountServiceREST.getBalance();
        User[] users = userService.getAllUsers(currentUser);
        System.out.println("-------------------------------------------\n" +
                "Your current account balance is : $" + balance );
        System.out.println("-------------------------------------------\n" +
                "User ID             " + "Name \n" +
                "-------------------------------------------\n" );
        for (User userlist : users) {
            if (!userlist.getId().equals(currentUser.getUser().getId()))
            System.out.println(userlist.getId()+ "                "+ userlist.getUsername());
        }
        Account[] accounts = accountServiceREST.listAccounts();
        System.out.println("-------------------------------------------\n");

            Long sendToUserID = consoleService.promptForLong("Please select the user ID that you'd like to send TEnmo bucks to (0 to cancel): ");
            System.out.println("You have selected User ID: " + sendToUserID + "\n");

            Double amountToSend = consoleService.promptForDouble("Please indicate the amount of TEBucks you'd like to send to User ID " + sendToUserID + ": ");

            Long sendFromUserID = currentUser.getUser().getId();
            Account senderAccount = accountServiceREST.getAccountByUserId(sendFromUserID);
            Long sendFrom = senderAccount.getAccountId();


            Account recieverAccount = accountServiceREST.getAccountByUserId(sendToUserID);
            Long sendTo = recieverAccount.getAccountId();

            if (amountToSend > 0 && amountToSend <= balance ) {
                transferServiceREST.createTransfer(2L, 2L, sendFrom, sendTo, amountToSend);

                accountServiceREST.withdrawAccount(senderAccount, sendFromUserID, amountToSend);

                accountServiceREST.depositAccount(recieverAccount, sendToUserID, amountToSend);
            }

            Double updatedBalance = accountServiceREST.getBalance();


        System.out.println("-------------------------------------------\n");
        System.out.println("Your transfer to User ID: " + sendToUserID + " for the amount of $" + amountToSend + " has been initiated. \n" +
                        "Your new account balance is: " + updatedBalance + "\n");
        }


	private void requestBucks() {
		// TODO Auto-generated method stub
		
	}

}
