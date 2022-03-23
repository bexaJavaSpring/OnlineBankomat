package uz.pdp.apponlineatm.service;

import uz.pdp.apponlineatm.model.User;

import static uz.pdp.apponlineatm.model.Messages.MessageHelper.WRONG_OPTION;
import static uz.pdp.apponlineatm.repository.Database.scannerInt;

public class UserMenyu {
    static BankomatServicePlan bankomatService = new BankomatServicePlan();
    static BankCardServicePlan bankCardService = new BankCardServicePlan();
    static TransActionServicePlan transactionService = new TransActionServicePlan();
    static UserServicePlan userService = new UserServicePlan();

    public void userMenu(User user) {

        switch (user.getRole()) {
            case ADMIN: {
                boolean adminMenu = true;
                while (true) {
                    System.out.println("User " + user.getUsername() + ", role : " + user.getRole());
                    System.out.println("1=>UserList. 2=>Bankomat Add. 3=>Bankomat Edit. 4=>Bankomat List.\n" +
                            "5=>Bankomat Delete. 6=>Activate Card 7=>Card Add. 8=>CardList\n" +
                            "9=>TransactionHistory All. 10=>Bankomat TransactionHis. 11=>Card TransactionHis . 12=>Transaction Type . 13=> BanCard Blocked. 0=>Logout ");
                    int adminOption = scannerInt.nextInt();
                    switch (adminOption) {
                        case 1: {
                            userService.users();
                            break;
                        }
                        case 2: {
                            bankomatService.Create();
                            break;
                        }
                        case 3: {
                            bankomatService.Update();
                            break;
                        }
                        case 4: {
                            bankomatService.Read();
                            break;
                        }
                        case 5: {
                            bankomatService.Delete();
                            break;
                        }
                        case 6: {
                            bankCardService.activateCard(user);
                            break;
                        }
                        case 7: {
                            bankCardService.createCard(user);
                            break;
                        }
                        case 8: {
                            bankCardService.cardList(user);
                            break;
                        }
                        case 9: {
                            transactionService.transactionList(user);
                            break;
                        }
                        case 10:
                            transactionService.transactionListByBankonat(user);
                            break;
                        case 11: {
                            transactionService.transactionListByCard(user);
                            break;
                        }
                        case 12:
                            transactionService.typeHistory(user);
                            break;
                        case 13: bankCardService.blockedcardList(user);
                        break;
                        case 0:
                            adminMenu = false;
                            return;
                        default: {
                            System.out.println(WRONG_OPTION);
                        }
                    }
                }
            }
            case USER: {
                boolean userMenu = true;
                while (true) {
                    System.out.println("User " + user.getUsername() + ", role : " + user.getRole());
                    System.out.println("1=>Add Card. 2=>My Card. 3=>Transaction(ExchangeMoney). 4=>Change Pin.\n" +
                            "5=>TransactionHistory All. 6=>Bankomat TransactionHis. 7=>Card TransactionHis. 8=>Balance 9=>ByTransactionType Hist 0=>Logout");
                    int adminOption = scannerInt.nextInt();
                    switch (adminOption) {
                        case 1: {
                            bankCardService.createCard(user);
                            break;
                        }
                        case 2: {
                            bankCardService.cardList(user);
                            break;
                        }
                        case 3: {
                            bankCardService.chooseTransactionType(user);
                            break;
                        }
                        case 4: {
                            bankCardService.changePin(user);
                            break;
                        }
                        case 5: {
                            transactionService.transactionList(user);
                            break;
                        }
                        case 6:
                            transactionService.transactionListByBankonat(user);
                            break;
                        case 7: {
                            transactionService.transactionListByCard(user);
                            break;
                        }
                        case 8: {
                            bankCardService.seeBalance(user);
                            break;
                        }
                        case 9: {
                            transactionService.typeHistory(user);
                            break;
                        }
                        case 0:
                            userMenu = false;
                            return;
                        default: {
                            System.out.println(WRONG_OPTION);
                        }
                    }
                }
            }
        }

    }
}
