package uz.pdp.apponlineatm.model;


import uz.pdp.apponlineatm.model.Enum.TransActionType;

public class TransActionHistory {
    private Integer id;
    private Bankomat bankomat;
    private BankCard bankCard;
    private String where;

    public String getWhere() {
        return where;
    }

    public void setWhere(String where) {
        this.where = where;
    }

    public TransActionHistory(Integer id, Bankomat bankomat, BankCard bankCard, String where, TransActionType type, double amount) {
        this.id = id;
        this.bankomat = bankomat;
        this.bankCard = bankCard;
        this.where = where;
        this.type = type;
        this.amount = amount;
    }

    private TransActionType type;
    private double amount;

    public TransActionHistory() {
    }

    public TransActionHistory(Integer id, Bankomat bankomat, BankCard bankCard, TransActionType type, double amount) {
        this.id = id;
        this.bankomat = bankomat;
        this.bankCard = bankCard;
        this.type = type;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Bankomat getBankomat() {
        return bankomat;
    }

    public void setBankomat(Bankomat bankomat) {
        this.bankomat = bankomat;
    }

    public BankCard getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public TransActionType getType() {
        return type;
    }

    public void setType(TransActionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransactionHistory{" +
                "id=" + id +
                ", bankomat=" + bankomat +
                ", bankCard=" + bankCard +
                ", type=" + type +
                ", amount=" + amount +
                '}';
    }
}
