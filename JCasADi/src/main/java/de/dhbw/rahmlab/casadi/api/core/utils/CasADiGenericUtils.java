package de.dhbw.rahmlab.casadi.api.core.utils;

import de.dhbw.rahmlab.casadi.api.core.wrapper.generictype.CasADiGenericWrapper;
import de.dhbw.rahmlab.casadi.impl.casadi.GenericType;
import de.dhbw.rahmlab.casadi.impl.casadi.TypeID;

public class CasADiGenericUtils {

    public static String typeName() {
        return GenericType.type_name();
    }

    public static String getTypeDescription(TypeID type) {
        return GenericType.get_type_description(type);
    }

    public static CasADiGenericWrapper constructFromTypeID(TypeID type) {
        return new CasADiGenericWrapper(GenericType.from_type(type));
    }

}
