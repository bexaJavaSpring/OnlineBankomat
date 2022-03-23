package uz.pdp.apponlineatm.service;


import uz.pdp.apponlineatm.model.BankCard;
import uz.pdp.apponlineatm.model.Enum.CardStatus;
import uz.pdp.apponlineatm.model.Enum.UserRole;
import uz.pdp.apponlineatm.model.User;
import uz.pdp.apponlineatm.service.InterfaceService.UserService;
import uz.pdp.apponlineatm.service.action.ExistAction;
import uz.pdp.apponlineatm.service.builder.UserBuilder;

import static uz.pdp.apponlineatm.model.Messages.MessageHelper.*;
import static uz.pdp.apponlineatm.repository.Database.*;

public class UserServicePlan implements UserService {
    static ExistAction existAction = new ExistAction();
    static UserMenyu userMenu = new UserMenyu();

@Override
    public void login() {
        System.out.println("1=>Login BankCard. 2=>Login Username");
        int option = scannerInt.nextInt();

        switch (option) {
            case 1:
                int count = 0;
                System.out.print("Enter cardNum:");
                long card = scannerLong.nextLong();
                BankCard bankCard = existAction.findBankCardNum(card);
                if (bankCard != null) {
                    boolean active = true;
                    while (active) {
                        if (count == 3) {
                            bankCard.setStatus(CardStatus.BLOCK);
                            System.out.println("Card Blocked");
                            return;
                        }
                        System.out.println("Enter pin or back=>0");
                        int pin = scannerInt.nextInt();
                        if (pin == 0) return;
                        BankCard card1 = existAction.findBankCardNumPin(card, pin);
                        if (card1 != null) {
                            userMenu.userMenu(card1.getUser());
                        } else {
                            count++;
                        }
                    }
                }
                break;
            case 2: {
                System.out.println("Enter username:");
                String username = scannerStr.nextLine();
                System.out.println("Enter password:");
                String password = scannerStr.nextLine();
                User user = existAction.checkUserLogin(username, password);
                if (user != null) {
                    System.out.println("Welcome " + user.getUsername());
                    userMenu.userMenu(user);

                } else {
                    System.out.println(NOT_FOUND);
                }
                break;
            }
        }

    }

@Override
    public void register() {
        System.out.println("Enter username:");
        String username = scannerStr.nextLine();
        System.out.println("Enter password:");
        String password = scannerStr.nextLine();
        System.out.println("Confirm password:");
        String confirmPassword = scannerStr.nextLine();

        if (!existAction.checkUser(username) && password.equals(confirmPassword)) {

            UserBuilder userBuilder = User::new;
            User user = userBuilder.create((int) (Math.random() * 10000), username, password, UserRole.USER);
            userList.add(user);
            System.out.println(CREATED);
        } else {
            System.out.println(USER_EXIST);
        }

    }

@Override
    public void users() {
        userList.stream().forEach(user -> {System.out.println("id : " + user.getId() + ", username : " + user.getUsername() + ", role:" + user.getRole());
                });
    }

}
