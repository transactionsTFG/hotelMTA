package common.converter;

import java.time.ZonedDateTime;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, String>{

    @Override
    public String convertToDatabaseColumn(ZonedDateTime attribute) {
        return ZonedDateUtils.parseString(attribute);
    }

    @Override
    public ZonedDateTime convertToEntityAttribute(String dbData) {
        Result<ZonedDateTime> zoneDateTime =  ZonedDateUtils.getZonedTime(dbData);
        if(!zoneDateTime.isSuccess())
            throw new RuntimeException("No podemos parsear: " + dbData);
        
        return zoneDateTime.getData();
    }
    
}
