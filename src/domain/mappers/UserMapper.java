package domain.mappers;

import data.models.User;
import domain.exceptions.UserNameException;
import domain.models.UserModel;

/**
 * Mapper pentru conversia între modelele de date {@link User} și modelele de domeniu {@link UserModel}.
 */
public class UserMapper {

    /**
     * Convertește un obiect de tip {@link User} într-un obiect de tip {@link UserModel}.
     *
     * @param user obiectul de tip {@link User} ce trebuie convertit
     * @return obiectul de tip {@link UserModel} rezultat din conversie
     */
    public static UserModel toModel(User user) {
        return new UserModel(user.getId(), user.getFirstName(), user.getLastName());
    }

    /**
     * Convertește un obiect de tip {@link UserModel} într-un obiect de tip {@link User}.
     * Aruncă o excepție de tip {@link RuntimeException} dacă apare o excepție de tip {@link UserNameException}.
     *
     * @param user obiectul de tip {@link UserModel} ce trebuie convertit
     * @return obiectul de tip {@link User} rezultat din conversie
     * @throws RuntimeException dacă apare o excepție {@link UserNameException}
     */
    public static User toData(UserModel user) {
        try {
            return new User(user.getId(), user.getFirstName(), user.getLastName());
        } catch (UserNameException e) {
            throw new RuntimeException(e);
        }
    }
}
