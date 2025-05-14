package com.patzn.paas.cutc.modbus.enums;

import lombok.Getter;
import lombok.ToString;

import com.serotonin.modbus4j.code.DataType;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * @see com.serotonin.modbus4j.code.DataType;
 */
@Getter
@ToString
public enum DataTypeEnum {

    BINARY(DataType.BINARY, "BINARY", "二进制"),

    TWO_BYTE_INT_UNSIGNED(DataType.TWO_BYTE_INT_UNSIGNED, "TWO_BYTE_INT_UNSIGNED", "2字节无符号整数"),
    TWO_BYTE_INT_SIGNED(DataType.TWO_BYTE_INT_SIGNED, "TWO_BYTE_INT_SIGNED", "2字节有符号整数"),
    TWO_BYTE_INT_UNSIGNED_SWAPPED(DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED, "TWO_BYTE_INT_UNSIGNED_SWAPPED", "2字节无符号整数(交换)"),
    TWO_BYTE_INT_SIGNED_SWAPPED(DataType.TWO_BYTE_INT_SIGNED_SWAPPED, "TWO_BYTE_INT_SIGNED_SWAPPED", "2字节有符号整数(交换)"),

    FOUR_BYTE_INT_UNSIGNED(DataType.FOUR_BYTE_INT_UNSIGNED, "FOUR_BYTE_INT_UNSIGNED", "4字节无符号整数"),
    FOUR_BYTE_INT_SIGNED(DataType.FOUR_BYTE_INT_SIGNED, "FOUR_BYTE_INT_SIGNED", "4字节有符号整数"),
    FOUR_BYTE_INT_UNSIGNED_SWAPPED(DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED, "FOUR_BYTE_INT_UNSIGNED_SWAPPED", "4字节无符号整数(交换)"),
    FOUR_BYTE_INT_SIGNED_SWAPPED(DataType.FOUR_BYTE_INT_SIGNED_SWAPPED, "FOUR_BYTE_INT_SIGNED_SWAPPED", "4字节有符号整数(交换)"),
    FOUR_BYTE_INT_UNSIGNED_SWAPPED_SWAPPED(DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED_SWAPPED, "FOUR_BYTE_INT_UNSIGNED_SWAPPED_SWAPPED", "4字节无符号整数(交换x2)"),
    FOUR_BYTE_INT_SIGNED_SWAPPED_SWAPPED(DataType.FOUR_BYTE_INT_SIGNED_SWAPPED_SWAPPED, "FOUR_BYTE_INT_SIGNED_SWAPPED_SWAPPED", "4字节有符号整数(交换x2)"),

    FOUR_BYTE_FLOAT(DataType.FOUR_BYTE_FLOAT, "FOUR_BYTE_FLOAT", "4字节浮点数"),
    FOUR_BYTE_FLOAT_SWAPPED(DataType.FOUR_BYTE_FLOAT_SWAPPED, "FOUR_BYTE_FLOAT_SWAPPED", "4字节浮点数(交换)"),
    FOUR_BYTE_FLOAT_SWAPPED_INVERTED(DataType.FOUR_BYTE_FLOAT_SWAPPED_INVERTED, "FOUR_BYTE_FLOAT_SWAPPED_INVERTED", "4字节浮点数(交换反转)"),

    EIGHT_BYTE_INT_UNSIGNED(DataType.EIGHT_BYTE_INT_UNSIGNED, "EIGHT_BYTE_INT_UNSIGNED", "8字节无符号整数"),
    EIGHT_BYTE_INT_SIGNED(DataType.EIGHT_BYTE_INT_SIGNED, "EIGHT_BYTE_INT_SIGNED", "8字节有符号整数"),
    EIGHT_BYTE_INT_UNSIGNED_SWAPPED(DataType.EIGHT_BYTE_INT_UNSIGNED_SWAPPED, "EIGHT_BYTE_INT_UNSIGNED_SWAPPED", "8字节无符号整数(交换)"),
    EIGHT_BYTE_INT_SIGNED_SWAPPED(DataType.EIGHT_BYTE_INT_SIGNED_SWAPPED, "EIGHT_BYTE_INT_SIGNED_SWAPPED", "8字节有符号整数(交换)"),
    EIGHT_BYTE_FLOAT(DataType.EIGHT_BYTE_FLOAT, "EIGHT_BYTE_FLOAT", "8字节浮点数"),
    EIGHT_BYTE_FLOAT_SWAPPED(DataType.EIGHT_BYTE_FLOAT_SWAPPED, "EIGHT_BYTE_FLOAT_SWAPPED", "8字节浮点数(交换)"),

    TWO_BYTE_BCD(DataType.TWO_BYTE_BCD, "TWO_BYTE_BCD", "2字节BCD"),
    FOUR_BYTE_BCD(DataType.FOUR_BYTE_BCD, "FOUR_BYTE_BCD", "4字节BCD"),
    FOUR_BYTE_BCD_SWAPPED(DataType.FOUR_BYTE_BCD_SWAPPED, "FOUR_BYTE_BCD_SWAPPED", "4字节BCD(交换)"),

    CHAR(DataType.CHAR, "CHAR", "字符"),
    VARCHAR(DataType.VARCHAR, "VARCHAR", "字符(变长)"),

    FOUR_BYTE_MOD_10K(DataType.FOUR_BYTE_MOD_10K, "FOUR_BYTE_MOD_10K", "4字节MOD10K"),
    SIX_BYTE_MOD_10k(DataType.SIX_BYTE_MOD_10K, "SIX_BYTE_MOD_10K", "6字节MOD10K"),
    EIGHT_BYTE_MOD_10K(DataType.EIGHT_BYTE_MOD_10K, "EIGHT_BYTE_MOD_10K", "8字节MOD10K"),

    FOUR_BYTE_MOD_10K_SWAPPED(DataType.FOUR_BYTE_MOD_10K_SWAPPED, "FOUR_BYTE_MOD_10K_SWAPPED", "4字节MOD10K(交换)"),
    SIX_BYTE_MOD_10K_SWAPPED(DataType.SIX_BYTE_MOD_10K_SWAPPED, "SIX_BYTE_MOD_10K_SWAPPED", "6字节MOD10K(交换)"),
    EIGHT_BYTE_MOD_10K_SWAPPED(DataType.EIGHT_BYTE_MOD_10K_SWAPPED, "EIGHT_BYTE_MOD_10K_SWAPPED", "8字节MOD10K(交换)"),

    ONE_BYTE_INT_UNSIGNED_LOWER(DataType.ONE_BYTE_INT_UNSIGNED_LOWER, "ONE_BYTE_INT_UNSIGNED_LOWER", "1字节无符号整数(低位)"),
    ONE_BYTE_INT_UNSIGNED_UPPER(DataType.ONE_BYTE_INT_UNSIGNED_UPPER, "ONE_BYTE_INT_UNSIGNED_UPPER", "1字节无符号整数(高位)"),
    ;

    private final int value;
    private final String name;
    private final String displayName;

    DataTypeEnum(int value, String name, String displayName) {
        this.value = value;
        this.name = name;
        this.displayName = displayName;
    }

    @Getter
    private static final List<String> nameList;
    @Getter
    private static final List<String> displayNameList;
    static {
        nameList = new LinkedList<>();
        displayNameList = new LinkedList<>();
        for (DataTypeEnum dataType : DataTypeEnum.values()) {
            nameList.add(dataType.getName());
            displayNameList.add(dataType.getDisplayName());
        }
    }

    public static DataTypeEnum ofValue(int value) {
        for (DataTypeEnum dataType : DataTypeEnum.values()) {
            if (dataType.value == value) {
                return dataType;
            }
        }
        return null;
    }

    public static DataTypeEnum ofName(String name) {
        if (name == null) {
            return null;
        }
        for (DataTypeEnum dataType : DataTypeEnum.values()) {
            if (Objects.equals(dataType.name, name)) {
                return dataType;
            }
        }
        return null;
    }

    public static DataTypeEnum ofDisplayName(String displayName) {
        if (displayName == null) {
            return null;
        }
        for (DataTypeEnum dataType : DataTypeEnum.values()) {
            if (Objects.equals(dataType.displayName, displayName)) {
                return dataType;
            }
        }
        return null;
    }

    public static int indexOfName(String name) {
        return nameList.indexOf(name);
    }

    public static int indexOfDisplayName(String displayName) {
        return displayNameList.indexOf(displayName);
    }
}
