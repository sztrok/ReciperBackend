package mappers;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;
import com.eat.it.eatit.backend.dto.ItemDTO;

import java.util.List;

public class FridgeMapper {
    public static FridgeDTO toDTO(Fridge fridge) {
        List<ItemDTO>
        return new FridgeDTO(fridge.getOwnerId(), fridge.getItems());
    }
}
