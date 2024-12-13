package common.converter;

import java.time.ZonedDateTime;

import common.dto.result.Result;
import common.utils.ZonedDateUtils;
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
            throw new RuntimeException("We cannot parse: " + dbData);
        
        return zoneDateTime.getData();
    }
    
}
