package br.com.ronaldozuchi.uteis;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Classe para converter a variavel do tipo LocalDateTime
 *
 * @author Ronaldo Zuchi
 *
 */
@Converter(autoApply = true)
public class LocalDateTimeAttibuteConverter implements AttributeConverter<LocalDateTime, Timestamp> {

	/**
	 * Faz a conversão de LocalDateTime em Timestamp quando persistir os dados
	 */
	@Override
	public Timestamp convertToDatabaseColumn(LocalDateTime attribute) {
		if (attribute != null)
			return Timestamp.valueOf(attribute);
		return null;
	}

	/**
	 * Transforma o Timestamp em LocalDateTime quando for realizada a consulta.
	 */
	@Override
	public LocalDateTime convertToEntityAttribute(Timestamp dbData) {
		if (dbData != null)
			return dbData.toLocalDateTime();
		return null;
	}

}
