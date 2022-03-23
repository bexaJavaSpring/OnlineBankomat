package uz.pdp.apponlineatm.model;


public class Bankomat {

   private Integer id;
   private String name;
   private double commissionFee;

   public Bankomat() {
   }

   public Bankomat(Integer id, String name, double commissionFee) {
      this.id = id;
      this.name = name;
      this.commissionFee = commissionFee;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public double getCommissionFee() {
      return commissionFee;
   }

   public void setCommissionFee(double commissionFee) {
      this.commissionFee = commissionFee;
   }

   @Override
   public String toString() {
      return "Bankomat{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", commissionFee=" + commissionFee +
              '}';
   }
}
