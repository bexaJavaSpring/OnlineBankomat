package uz.pdp.apponlineatm.service.builder;

import uz.pdp.apponlineatm.model.Bankomat;

public interface BankomatBuilder {
    Bankomat create(Integer id, String name, double commissionFee);    // Bankomatni yaratish
}
