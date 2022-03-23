package uz.pdp.apponlineatm.repository;

import uz.pdp.apponlineatm.model.BankCard;
import uz.pdp.apponlineatm.model.Enum.UserRole;
import uz.pdp.apponlineatm.model.TransActionHistory;
import uz.pdp.apponlineatm.model.User;
import uz.pdp.apponlineatm.model.Bankomat;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Database {
    public static Scanner scannerStr = new Scanner(System.in);
    public static Scanner scannerInt = new Scanner(System.in);
    public static Scanner scannerDou = new Scanner(System.in);
    public static Scanner scannerLong = new Scanner(System.in);

    public static List<Bankomat> bankomatList = new ArrayList<>();
    public static List<User> userList = new ArrayList<>(Arrays.asList(new User(1, "Bexruz", "0000", UserRole.ADMIN)));
    public static List<BankCard> cardList = new ArrayList<>();
    public static List<TransActionHistory> transactionHistoryList = new ArrayList<>();


}
