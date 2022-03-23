package uz.pdp.apponlineatm.service;

import uz.pdp.apponlineatm.model.BankCard;
import uz.pdp.apponlineatm.model.Bankomat;
import uz.pdp.apponlineatm.model.Enum.TransActionType;
import uz.pdp.apponlineatm.model.TransActionHistory;
import uz.pdp.apponlineatm.model.User;
import uz.pdp.apponlineatm.service.InterfaceService.BanCardService;
import uz.pdp.apponlineatm.service.action.ExistAction;

import static javax.accessibility.AccessibleState.ACTIVE;
import static jdk.nashorn.tools.Shell.SUCCESS;
import static sun.security.provider.certpath.BuildStep.FAIL;
import static uz.pdp.apponlineatm.model.Enum.CardStatus.BLOCK;
import static uz.pdp.apponlineatm.model.Enum.CardStatus.UNBLOCK;
import static uz.pdp.apponlineatm.model.Messages.MessageHelper.*;
import static uz.pdp.apponlineatm.repository.Database.*;

public class BankCardServicePlan implements BanCardService {
    static ExistAction existAction = new ExistAction();
    static BankomatServicePlan bankomatService = new BankomatServicePlan();

@Override
    public void createCard(User user) {
        System.out.print("Enter card number:");
        Long number = scannerLong.nextLong();
        System.out.print("Enter pin:");
        int pin = scannerInt.nextInt();

        if (!existAction.existCard(number)) {
            BankCard card = new BankCard((int) (Math.random() * 10000), number, pin, UNBLOCK, user);
            cardList.add(card);
            System.out.println(CREATED);
        } else {
            System.out.println(EXIST);
        }

    }
@Override
    public void blockedcardList(User user) {
        cardList.stream()
                .filter(bankCard -> bankCard.getStatus().equals(BLOCK))
                .forEach(card -> {
                    System.out.println("Card : " + card.getCardNumber() + ", id: " + card.getId() + ", status : " + card.getStatus());
                });

    }
@Override
    public void payIndustry(User user) {
        bankomatService.Read();          //Bankomatlani listi chaqirilyapti

        System.out.println("Enter bankomat id:");    // id sini tanlaymiz
        int idBAN = scannerInt.nextInt();
        Bankomat bankomat = existAction.findBankomat(idBAN); // shu bankomat id sini topamiz
        if (bankomat != null) {  // agar bankomat nalga teng bo'lsa Cardlistla keladi,keyin cart ni id sini kiritamiz
            cardList(user);
            System.out.println("Enter you card id:");
            int id = scannerInt.nextInt();
            BankCard card = existAction.findBankCard(id);   //keyin shu card ni id sini topamiz
            if (card != null && card.getStatus().equals(UNBLOCK)) {  //agar card nalga teng bo'lsa va bloklanmagan bo'lsa

                System.out.println("Enter place you want to pay");  // pul yechib oladigan joyimizni kiritamiz
                String where = scannerStr.nextLine();

                System.out.println("Enter amount:");     //miqdorini kiritamiz
                double amount = scannerDou.nextDouble();
                double total = (bankomat.getCommissionFee() * amount / 100) + amount;  // opshi summa  amountga qo'shamiz bankomatni foiz olishiga

                if (card.getBalance() >= total) {         // bu yerda cartani balansi totaldan katta yoki teng bo'lsa
                    card.setBalance(card.getBalance() - total);  // balansimizga o'zimizni avvalgi balnsidan total ayirilgani qoladi
                    TransActionHistory transactionHistory = new TransActionHistory((int) (Math.random() * 10000), bankomat, card,
                            where,
                            TransActionType.PAY_INDUSTRY,
                            amount
                    );
                    transactionHistoryList.add(transactionHistory);  // transaction historiysiga qo'shamiz hozirgi payIndustryni
                    System.out.println(SUCCESS);
                } else {
                    System.out.println(FAIL);  // agar balansimiz totaldan kichik bo'lsa pul tashash kk bo'ladi
                }

            } else {   // agar shu bankomat nol bo'lsa Not found
                System.out.println(NOT_FOUND);
            }
        }
    }
@Override
    public void cardList(User user) {
        switch (user.getRole()) {
            case ADMIN:                // agar Admin bo'lsa card lani o'zini ro'yxatini ko'radi
                cardList.stream()
                        .forEach(card -> {
                            System.out.println("Card : " + card.getCardNumber() + ", id: " + card.getId() + ", status : " + card.getStatus());
                        });
                break;
            case USER: {          //agar user bo'lsa o'zini cardlarini listini ko'radi
                cardList.stream().filter(card -> card.getUser().equals(user)).forEach(card -> {
                            System.out.println("Card : " + card.getCardNumber() + ", id: " + card.getId() + ", balance : " + card.getBalance() + ", status : " + card.getStatus());
                        });
                break;
            }
        }

    }

@Override
    public void activateCard(User user) {   // cartani aktivlashtirish un avval bloklangan cardlani listi chaqiriladi
        blockedcardList(user);
        System.out.println("Enter id");  // id si kiritiladi
        int id = scannerInt.nextInt();
        BankCard card = existAction.findBankCard(id);    //keyin shunaqa cardni id sini topamiz
        if (card != null && card.getStatus().equals(BLOCK)) {    // agar shunaqa card bo'lsa statusi bloklangan bo'lsa
            card.setStatus(UNBLOCK);      // cartani statusini set qilib unblok yani aktiv qilib qoyamiz
            System.out.println(SUCCESS);
        } else {
            System.out.println(ACTIVE);
        }

    }

@Override
    public void fillBalance(User user) {
        bankomatService.Read();

        System.out.println("Enter bankomat id:");
        int bankomatId = scannerInt.nextInt();
        Bankomat bankomat = existAction.findBankomat(bankomatId);
        if (bankomat != null) {
            cardList(user);
            System.out.println("Enter id:");
            int id = scannerInt.nextInt();
            cardList(user);
            BankCard card = existAction.findBankCard(id);
            if (card != null && card.getStatus().equals(UNBLOCK)) {
                System.out.println("Enter amount");
                double amount = scannerDou.nextDouble();
                if (amount > 0) {
                    card.setBalance(card.getBalance() + amount);
                    TransActionHistory transaction = new TransActionHistory((int) (Math.random() * 10000), bankomat, card,
                            card.getUser().getUsername(),
                            TransActionType.FILL_BALANCE, amount);
                    transactionHistoryList.add(transaction);

                    System.out.println(SUCCESS);
                } else {
                    System.out.println(FAIL);
                }
            } else {
                System.out.println(FAIL);
            }
        } else {
            System.out.println(NOT_FOUND);
        }
    }
@Override
    public void changePin(User user) {  // kartani pinkodini o'zgartirish

        cardList(user);
        System.out.println("Enter card id:"); // id si kiritiladi
        int id = scannerInt.nextInt();
        BankCard bankCard = existAction.findBankCard(id);  // keyin kartani Bancard topiladi
        if (bankCard != null && bankCard.getStatus().equals(UNBLOCK)) {  // agar o'sha bankli karta bo'lsa
            System.out.println("Enter old pin:");  // keyin eski kodini kiritamiz
            int oldPin = scannerInt.nextInt();
            if (bankCard.getPinCode() == oldPin) {      // bancardni pinkodi eski pinkodga teng bo'lsa
                System.out.println("Enter new pin:");   // yangi pinkod kiritamiz
                int newPin = scannerInt.nextInt();
                System.out.println("Enter Confirm pin:");
                int conPin = scannerInt.nextInt();
                if (newPin == conPin) {    //2 marta kirityapmiz
                    bankCard.setPinCode(newPin);  // bancartga set qilib yangi pinkodni almashtiramiz
                    System.out.println(SUCCESS);
                } else {
                    System.out.println(CONFIMATION_FAIL);
                }
            } else {
                System.out.println(FAIL);
            }
        } else {
            System.out.println(NOT_FOUND);
        }

    }
@Override
    public void cardToCard(User user) {
        bankomatService.Read();

        System.out.println("Enter bankomat id");
        int idBAN = scannerInt.nextInt();
        Bankomat bankomat = existAction.findBankomat(idBAN);
        if (bankomat != null) {
            cardList(user);
            System.out.println("Enter you card id:");
            int id = scannerInt.nextInt();
            BankCard card = existAction.findBankCard(id);
            if (card != null && card.getStatus().equals(UNBLOCK)) {

                System.out.println("Enter card number you want to transfer");
                long cardNUmber = scannerLong.nextLong();

                System.out.println("Enter amount:");
                double amount = scannerDou.nextDouble();
                double total = (bankomat.getCommissionFee() * amount / 100) + amount;

                if (card.getBalance() >= total) {
                    card.setBalance(card.getBalance() - total);
                    TransActionHistory transactionHistory = new TransActionHistory(
                            (int) (Math.random() * 10000),
                            bankomat,
                            card,
                            "" + cardNUmber,
                            TransActionType.CART_TO_CARD,
                            amount
                    );
                    transactionHistoryList.add(transactionHistory);
                    System.out.println(SUCCESS);
                } else {
                    System.out.println(FAIL);
                }

            } else {
                System.out.println(NOT_FOUND);
            }
        } else {
            System.out.println(NOT_FOUND);
        }


    }
@Override
    public void withDraw(BankCard card) {
        bankomatService.Read();

        System.out.println("Enter bankomat id");
        int id = scannerInt.nextInt();
        Bankomat bankomat = existAction.findBankomat(id);
        if (bankomat != null) {
            System.out.println("Enter amount");
            double amount = scannerDou.nextDouble();
            double total = amount + amount * bankomat.getCommissionFee() / 100;
            if (card.getBalance() >= total) {
                card.setBalance(card.getBalance() - total);
                TransActionHistory transactionHistory = new TransActionHistory(
                        (int) (Math.random() * 10000),
                        bankomat,
                        card,
                        "out",
                        TransActionType.WITHDRAW_MONEY,
                        amount
                );
                transactionHistoryList.add(transactionHistory);
                System.out.println(SUCCESS);
            } else {
                System.out.println(FAIL);
            }
        } else {
            System.out.println(NOT_FOUND);
        }
    }



    public void chooseTransactionType(User user) {

        while (true) {
            System.out.println("1=>Withdraw 2=>FillBalance 3=>CardToCard. 4=>PaySomeWhere 5=>Back");
            int option = scannerInt.nextInt();
            switch (option) {
                case 1: {
                    cardList(user);
                    System.out.println("Enter card id:");
                    int id = scannerInt.nextInt();
                    BankCard card = existAction.findBankCard(id);
                    if (card != null && card.getStatus().equals(UNBLOCK)) {
                        withDraw(card);
                    } else {
                        System.out.println(FAIL);
                    }
                    break;
                }
                case 2: {
                    fillBalance(user);
                    break;
                }
                case 3:
                    cardToCard(user);
                    break;
                case 4:
                    payIndustry(user);
                    break;
                case 5:
                    return;
            }
        }
    }

    public void seeBalance(User user) {   // kartani balansini ko'rish un shu cardlist chaqirib qoyamiz
        cardList(user);
    }


}
