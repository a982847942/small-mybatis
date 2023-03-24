package nuaa.zk.s06.type;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * @author brain
 * @version 1.0
 * @date 2023/3/22 21:09
 */
public enum JDBCType {
    INTEGER(Types.INTEGER),
    FLOAT(Types.FLOAT),
    DOUBLE(Types.DOUBLE),
    DECIMAL(Types.DECIMAL),
    VARCHAR(Types.VARCHAR),
    TIMESTAMP(Types.TIMESTAMP);

    private final int code;

    private static Map<Integer,JDBCType> codeLookup = new HashMap<>();
    static{
        for (JDBCType type : JDBCType.values()) {
            codeLookup.put(type.getCode(),type);
        }
    }
    JDBCType(int code) {
        this.code = code;
    }
    public JDBCType forCode(int code){
        return codeLookup.get(code);
    }

    public int getCode() {
        return code;
    }
}
