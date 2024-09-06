package mappers;

import com.eat.it.eatit.backend.data.Account;
import com.eat.it.eatit.backend.dto.AccountDTO;

public class AccountMapper {
    public static AccountDTO toDTO(Account account) {
        return new AccountDTO(account.getUsername(), account.getMail(),
                account.getFridge(), account.getRecipes(), account.isPremium());
    }
}
