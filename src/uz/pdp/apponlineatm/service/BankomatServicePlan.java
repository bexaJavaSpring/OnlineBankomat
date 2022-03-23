package uz.pdp.apponlineatm.service;


import uz.pdp.apponlineatm.model.Bankomat;

import uz.pdp.apponlineatm.service.InterfaceService.Bankomatservice;
import uz.pdp.apponlineatm.service.action.ExistAction;
import uz.pdp.apponlineatm.service.builder.BankomatBuilder;

import static jdk.nashorn.tools.Shell.SUCCESS;
import static sun.security.provider.certpath.BuildStep.FAIL;
import static uz.pdp.apponlineatm.model.Messages.MessageHelper.*;
import static uz.pdp.apponlineatm.repository.Database.*;

public class BankomatServicePlan implements Bankomatservice {
    static ExistAction existAction = new ExistAction();


    @Override
    public void Create() {
        System.out.println("Enter name:");
        String name = scannerStr.nextLine();
        System.out.println("Enter commission:");
        double fee = scannerDou.nextDouble();
        if (!existAction.checkBankomat(name)) {   //agar shunday nomli bankomat bo'lmasa database da
            BankomatBuilder bankomatBuilder = Bankomat::new;
            Bankomat bankomat = bankomatBuilder.create((int) (Math.random() * 10000), name, fee);
            bankomatList.add(bankomat);      // qo'shib qo'yamiz
            System.out.println(CREATED);
        } else {
            System.out.println(EXIST);  // bor bo'lsa bunday bankomat bor deymiz
        }
    }

    @Override
    public void Read() {
        bankomatList.stream()
                .forEach(bankomat -> {
                    System.out.println("ID:" + bankomat.getId() + ", name : " + bankomat.getName() + ", fee : " + bankomat.getCommissionFee());
                });
    }
    @Override
    public void Update() {
        Read();                   //listi chaqirilib qoyiladi
        System.out.println("Enter id:");
        int id = scannerInt.nextInt();
        Bankomat bankomat = existAction.findBankomat(id);     //bakomat topiladi
        if (bankomat != null) {                 //agar bor bo'lsa yani bankomat nolga teng bo'lmasa
            System.out.println("Enter commissionFee:");
            double fee = scannerDou.nextDouble();
            if (fee > 0) {       // comissionfee si 0 dan katta bo'lishi ham tekshiriladi
                bankomat.setCommissionFee(fee);    // bankomatni set qilib avalgisiga almashtirib qo'yamiz
                System.out.println(SUCCESS);
            } else {
                System.out.println(FAIL);
            }
        } else {
            System.out.println(NOT_FOUND);
        }
    }

    @Override
    public void Delete() {
        Read();                  //listi chaqiriladi
        System.out.println("Enter id:");      //id si kiritiladi
        int id = scannerInt.nextInt();
        Bankomat bankomat = existAction.findBankomat(id);     //bankomat topilsa yani nolga teng bo'lmasa'
        if (bankomat != null) {
            bankomatList.remove(bankomat);  // database dagi bankomat listladan o'sha id si bo'yicha tanlangan bankomat remove qilib o'chiriladi
            System.out.println(SUCCESS);
        } else {
            System.out.println(NOT_FOUND);
        }
    }
}
