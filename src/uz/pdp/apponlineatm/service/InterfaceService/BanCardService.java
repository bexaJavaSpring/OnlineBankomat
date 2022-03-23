package uz.pdp.apponlineatm.service.InterfaceService;

import uz.pdp.apponlineatm.model.BankCard;
import uz.pdp.apponlineatm.model.User;

public interface BanCardService {          // Admin va userni qiladigan ishlari
    void createCard(User user);

    void blockedcardList(User user);

    void payIndustry(User user);

    void cardList(User user);

    void activateCard(User user);

    void fillBalance(User user);

    void changePin(User user);

    void cardToCard(User user);

    void withDraw(BankCard card);


}
