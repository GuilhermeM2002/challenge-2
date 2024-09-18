package br.com.challenge2.order.infra.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter
class SetOfLongsConverter : AttributeConverter<Set<Long>, String> {
    override fun convertToDatabaseColumn(attribute: Set<Long>?): String {
        return attribute?.joinToString(",") ?: ""
    }

    override fun convertToEntityAttribute(dbData: String?): Set<Long> {
        return dbData?.split(",")?.mapNotNull { it.toLongOrNull() }?.toSet() ?: emptySet()
    }
}