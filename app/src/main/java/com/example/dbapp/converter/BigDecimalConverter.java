package com.example.dbapp.converter;

import java.math.BigDecimal;
import androidx.room.TypeConverter;

public class BigDecimalConverter {

    @TypeConverter
    public static BigDecimal fromString(String value) {
        return value == null ? null : new BigDecimal(value);
    }

    @TypeConverter
    public static String bigDecimalToString(BigDecimal bigDecimal) {
        return bigDecimal == null ? null : bigDecimal.toPlainString();
    }
}
