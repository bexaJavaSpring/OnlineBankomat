package uz.pdp.apponlineatm;


import uz.pdp.apponlineatm.service.UserServicePlan;

import static uz.pdp.apponlineatm.model.Messages.MessageHelper.WRONG_OPTION;
import static uz.pdp.apponlineatm.repository.Database.scannerInt;

public class Main {
    static UserServicePlan userServicePlan = new UserServicePlan();

    public static void main(String[] args) {

        boolean active = true;
        while (active) {
            System.out.println("1=>Login. 2=>Register. 3=>Exit");
            System.out.print("Select:");
            int option = scannerInt.nextInt();

            switch (option) {
                case 1:
                    userServicePlan.login();
                    break;
                case 2:
                    userServicePlan.register();
                    break;
                case 3:
                    active = false;
                    break;
                default:
                    System.out.println(WRONG_OPTION);
                    break;
            }
        }

    }
}
