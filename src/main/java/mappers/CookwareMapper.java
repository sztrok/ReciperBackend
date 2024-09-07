package mappers;

import com.eat.it.eatit.backend.data.Cookware;
import com.eat.it.eatit.backend.dto.CookwareDTO;

import java.util.HashSet;
import java.util.Set;

public class CookwareMapper {

    private CookwareMapper() {
    }

    public static CookwareDTO toDto(Cookware cookware) {
        return new CookwareDTO(cookware.getName());
    }

    public static Cookware toEntity(CookwareDTO cookware) {
        return new Cookware(cookware.getName());
    }

    public static Set<CookwareDTO> toDTOSet(Set<Cookware> cookwares) {
        Set<CookwareDTO> cookwareDTOSet = new HashSet<>();
        for(Cookware cookware : cookwares) {
            cookwareDTOSet.add(new CookwareDTO(cookware.getName()));
        }
        return cookwareDTOSet;
    }

    public static Set<Cookware> toEntitySet(Set<CookwareDTO> cookwares) {
        Set<Cookware> cookwareEntitySet = new HashSet<>();
        for(CookwareDTO cookware : cookwares) {
            cookwareEntitySet.add(new Cookware(cookware.getName()));
        }
        return cookwareEntitySet;
    }
}
