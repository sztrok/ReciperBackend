package mappers;

import com.eat.it.eatit.backend.data.Fridge;
import com.eat.it.eatit.backend.dto.FridgeDTO;

public class FridgeMapper {

    private FridgeMapper() {
    }

    public static FridgeDTO toDTO(Fridge fridge) {
        return new FridgeDTO(
                fridge.getOwnerId(),
                ItemMapper.toDTOSet(fridge.getItems()));
    }

    public static Fridge toEntity(FridgeDTO fridgeDTO) {
        return new Fridge(
                fridgeDTO.getOwnerId(),
                ItemMapper.toEntitySet(fridgeDTO.getItems()));
    }
}
