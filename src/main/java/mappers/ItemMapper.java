package mappers;

import com.eat.it.eatit.backend.data.Item;
import com.eat.it.eatit.backend.dto.ItemDTO;

public class ItemMapper {
    public static ItemDTO toDTO(Item item) {
        return new ItemDTO(item.getName(), item.getBarcode(), item.getCaloriesPer100g(),
                item.getProteinPer100G(), item.getFatPer100G(), item.getCarbsPer100G());
    }

    public static Item toEntity(ItemDTO itemDTO) {
        return new Item(itemDTO.getName(), itemDTO.getBarcode(), itemDTO.getCaloriesPer100g(),
                itemDTO.getProteinPer100G(), itemDTO.getFatPer100G(), itemDTO.getCarbsPer100G());
    }
}
