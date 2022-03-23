package uz.pdp.apponlineatm.service;

import uz.pdp.apponlineatm.model.BankCard;
import uz.pdp.apponlineatm.model.Bankomat;
import uz.pdp.apponlineatm.model.Enum.TransActionType;
import uz.pdp.apponlineatm.model.TransActionHistory;
import uz.pdp.apponlineatm.model.User;
import uz.pdp.apponlineatm.service.InterfaceService.TransactionService;
import uz.pdp.apponlineatm.service.action.ExistAction;

import static uz.pdp.apponlineatm.model.Messages.MessageHelper.NOT_FOUND;
import static uz.pdp.apponlineatm.model.Messages.MessageHelper.WRONG_OPTION;
import static uz.pdp.apponlineatm.repository.Database.*;

public class TransActionServicePlan implements TransactionService {
    static ExistAction existAction = new ExistAction();

@Override
    public void transactionList(User user) {        //transaksiyalar listi
        switch (user.getRole()) {
            case ADMIN: {        // admin bo'lsa prosta birorta userni kartasini historiyasini listini ko'radi
                transactionHistoryList.stream().forEach(transactionHistory ->
                        {
                            System.out.println("Type     : " + transactionHistory.getType());
                            System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                            System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                            System.out.println("Amount   : " + transactionHistory.getAmount());
                            System.out.println("Where    : " + transactionHistory.getWhere());
                            System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                            System.out.println("Total    : " +
                                    (transactionHistory.getAmount() +
                                            (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));

                            System.out.println("--------------------------------------------");
                        });
                break;
            }
            case USER: {             // agar user bo'lsa yani o'sha odam o'zini kartasini history sini listini ko'radi
                transactionHistoryList.stream().filter(transactionHistory -> transactionHistory.getBankCard().getUser().equals(user)).forEach(transactionHistory ->
                        {
                            System.out.println("Type     : " + transactionHistory.getType());
                            System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                            System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                            System.out.println("Amount   : " + transactionHistory.getAmount());
                            System.out.println("Where    : " + transactionHistory.getWhere());
                            System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                            System.out.println("Total    : " +
                                    (transactionHistory.getAmount() +
                                            (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));
                            System.out.println("--------------------------------------------");

                        });
                break;
            }
        }
    }
@Override
    public void transactionListByCard(User user) {
        transactionList(user);
        System.out.println("Enter cardnumber too see total transaction");
        long number = scannerLong.nextLong();
        BankCard card = existAction.findBankCardNum(number);
        if (card != null) {
            switch (user.getRole()) {
                case ADMIN: {
                    double total = 0;
                    for (TransActionHistory transactionHistory : transactionHistoryList) {
                        if (transactionHistory.getBankCard().equals(card)) {
                            System.out.println("Type     : " + transactionHistory.getType());
                            System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                            System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                            System.out.println("Amount   : " + transactionHistory.getAmount());
                            System.out.println("Where    : " + transactionHistory.getWhere());
                            System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                            System.out.println("Total    : " +
                                    (transactionHistory.getAmount() +
                                            (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));
                            total += (transactionHistory.getAmount() +
                                    (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100));
                            System.out.println("--------------------------------------------");
                        }

                    }
                    System.out.println("Total : " + total);
                    System.out.println("--------------------------------------------");
                }
                case USER: {
                    double total = 0;
                    for (TransActionHistory transactionHistory : transactionHistoryList) {
                        if (transactionHistory.getBankCard().equals(card) && transactionHistory.getBankCard().getUser().equals(user)) {
                            System.out.println("Type     : " + transactionHistory.getType());
                            System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                            System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                            System.out.println("Amount   : " + transactionHistory.getAmount());
                            System.out.println("Where    : " + transactionHistory.getWhere());
                            System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                            System.out.println("Total    : " +
                                    (transactionHistory.getAmount() +
                                            (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));
                            total += (transactionHistory.getAmount() +
                                    (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100));
                            System.out.println("--------------------------------------------");
                        }


                    }
                    System.out.println("Total : " + total);
                    System.out.println("--------------------------------------------");
                }
            }


        } else {
            System.out.println(NOT_FOUND);
        }

    }
@Override
    public void transactionListByBankonat(User user) {
        bankomatList.stream()
                .forEach(bankomat -> {
                    System.out.println("id:" + bankomat.getId() + ", name : " + bankomat.getName());
                });
        System.out.println("Enter bankomat id");
        int bankomatId = scannerInt.nextInt();
        Bankomat bankomat = existAction.findBankomat(bankomatId);
        if (bankomat != null) {
            switch (user.getRole()) {
                case ADMIN: {
                    transactionHistoryList.stream()
                            .filter(transactionHistory -> transactionHistory.getBankomat().equals(bankomat))
                            .forEach(transactionHistory ->
                            {
                                System.out.println("Type     : " + transactionHistory.getType());
                                System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                                System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                                System.out.println("Amount   : " + transactionHistory.getAmount());
                                System.out.println("Where    : " + transactionHistory.getWhere());
                                System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                                System.out.println("Total    : " +
                                        (transactionHistory.getAmount() +
                                                (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));
                                System.out.println("--------------------------------------------");


                            });
                    break;
                }
                case USER: {
                    transactionHistoryList.stream()
                            .filter(transactionHistory -> transactionHistory.getBankCard().getUser().equals(user)
                                    && transactionHistory.getBankomat().equals(bankomat))
                            .forEach(transactionHistory ->
                            {
                                System.out.println("Type     : " + transactionHistory.getType());
                                System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                                System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                                System.out.println("Amount   : " + transactionHistory.getAmount());
                                System.out.println("Where    : " + transactionHistory.getWhere());
                                System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                                System.out.println("Total    : " +
                                        (transactionHistory.getAmount() +
                                                (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));
                                System.out.println("--------------------------------------------");

                            });
                    break;
                }

            }
        } else {
            System.out.println(NOT_FOUND);
        }
    }

@Override
    public void typeHistory(User user) {
        System.out.println("1=>CARD TO CARD 2=>WITHDRAW 3=>FILL 4=>PAY WHERE");
        TransActionHistory transactionHistory = new TransActionHistory();
        int option = scannerInt.nextInt();
        switch (option) {
            case 1:
                transactionHistory.setType(TransActionType.CART_TO_CARD);
                typeHistoryTr(transactionHistory.getType(), user);
                break;
            case 2:
                transactionHistory.setType(TransActionType.WITHDRAW_MONEY);
                typeHistoryTr(transactionHistory.getType(), user);
                break;
            case 3:
                transactionHistory.setType(TransActionType.FILL_BALANCE);
                typeHistoryTr(transactionHistory.getType(), user);
                break;
            case 4:
                transactionHistory.setType(TransActionType.PAY_INDUSTRY);
                typeHistoryTr(transactionHistory.getType(), user);
                break;
            default:
                System.out.println(WRONG_OPTION);
                break;
        }
    }

    public void typeHistoryTr(TransActionType transactionType, User user) {
        switch (user.getRole()) {
            case ADMIN: {
                double total = 0;
                for (TransActionHistory transactionHistory : transactionHistoryList) {
                    if (transactionHistory.getType().equals(transactionType)) {
                        System.out.println("Type     : " + transactionHistory.getType());
                        System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                        System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                        System.out.println("Amount   : " + transactionHistory.getAmount());
                        System.out.println("Where    : " + transactionHistory.getWhere());
                        System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                        System.out.println("Total    : " +
                                (transactionHistory.getAmount() +
                                        (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));
                        total += (transactionHistory.getAmount() +
                                (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100));
                        System.out.println("--------------------------------------------");
                    }

                }
                System.out.println("Total : " + total);
                System.out.println("--------------------------------------------");
                break;
            }
            case USER: {
                double total = 0;
                for (TransActionHistory transactionHistory : transactionHistoryList) {
                    if (transactionHistory.getType().equals(transactionType) && transactionHistory.getBankCard().getUser().equals(user)) {
                        System.out.println("Type     : " + transactionHistory.getType());
                        System.out.println("Card     : " + transactionHistory.getBankCard().getCardNumber());
                        System.out.println("Bankomat : " + transactionHistory.getBankomat().getName());
                        System.out.println("Amount   : " + transactionHistory.getAmount());
                        System.out.println("Where    : " + transactionHistory.getWhere());
                        System.out.println("Fee      : " + transactionHistory.getBankomat().getCommissionFee());
                        System.out.println("Total    : " +
                                (transactionHistory.getAmount() +
                                        (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100)));
                        total += (transactionHistory.getAmount() +
                                (transactionHistory.getAmount() * transactionHistory.getBankomat().getCommissionFee() / 100));
                        System.out.println("--------------------------------------------");
                    }


                }
                System.out.println("Total : " + total);
                System.out.println("--------------------------------------------");
                break;
            }
        }
    }
}
