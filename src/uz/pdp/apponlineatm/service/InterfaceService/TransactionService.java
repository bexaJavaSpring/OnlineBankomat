package uz.pdp.apponlineatm.service.InterfaceService;

import uz.pdp.apponlineatm.model.User;

public interface TransactionService {
    void transactionList(User user);

    void transactionListByCard(User user);

    void transactionListByBankonat(User user);

    void typeHistory(User user);
}
