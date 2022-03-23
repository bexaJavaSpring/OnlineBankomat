package uz.pdp.apponlineatm.service.builder;

import uz.pdp.apponlineatm.model.Enum.UserRole;
import uz.pdp.apponlineatm.model.User;
@FunctionalInterface
public interface UserBuilder {
    User create(Integer id, String username, String password, UserRole role);     //Userni yaratish

}
