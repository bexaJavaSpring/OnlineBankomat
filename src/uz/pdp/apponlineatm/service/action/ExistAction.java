package uz.pdp.apponlineatm.service.action;

import uz.pdp.apponlineatm.model.Bankomat;
import uz.pdp.apponlineatm.model.BankCard;
import uz.pdp.apponlineatm.model.User;

import static uz.pdp.apponlineatm.repository.Database.*;

public class ExistAction {
    public boolean checkUser(String username) {          // Userligini tekshirish
        for (User user : userList) if (user.getUsername().equals(username)) return true;
        return false;
    }

    public User checkUserLogin(String username, String password) {    // userni loginini tekshirish
        for (User user : userList)
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        return null;
    }

    public boolean checkBankomat(String name) {       //Bankomatni tekshirish
        for (Bankomat bankomat : bankomatList) {
            if (bankomat.getName().equals(name))
                return true;
        }
        return false;
    }

    public Bankomat findBankomat(int id) {        //Bankomatni topish
        return bankomatList.stream()
                .filter(bankomat -> bankomat.getId() == id)
                .findFirst().orElse(null);
    }

    public boolean existCard(Long cardnumber) {           // Card bor yoqligini tekshirish
        for (BankCard bankCard : cardList) {
            if (bankCard.getCardNumber() == cardnumber)
                return true;
        }

        return false;
    }

    public BankCard findBankCard(int id) {             // Bancartni topish
        for (BankCard card : cardList) {
            if (card.getId() == id)
                return card;
        }
        return null;
    }

    public BankCard findBankCardNum(long id) {             //bancardni nomerini topish
        for (BankCard card : cardList) {
            if (card.getCardNumber() == id)
                return card;
        }
        return null;
    }

    public BankCard findBankCardNumPin(Long num, int pin) {     //BabCardni pinkodini tiopish
        return cardList.stream()
                .filter(card -> card.getCardNumber().equals(num) && card.getPinCode() == pin)
                .findFirst().orElse(null);
    }
}
