package br.com.prodtime.domain

import java.time.LocalDate
import java.time.MonthDay

sealed interface HolidayDefinition {
    val name: String
}

data class AnnualHoliday(
    override val name: String,
    val monthDay: MonthDay,
) : HolidayDefinition {
    init {
        require(name.isNotBlank()) { "O nome do feriado não pode ser vazio." }
    }
}

data class SpecificDateHoliday(
    override val name: String,
    val date: LocalDate,
) : HolidayDefinition {
    init {
        require(name.isNotBlank()) { "O nome do feriado não pode ser vazio." }
    }
}

object HolidayResolver {
    fun resolve(
        definitions: Collection<HolidayDefinition>,
        startDate: LocalDate,
        endDate: LocalDate,
    ): Set<LocalDate> {
        require(startDate <= endDate) {
            "A data inicial deve ser anterior ou igual à data final."
        }

        return buildSet {
            definitions.forEach { definition ->
                when (definition) {
                    is AnnualHoliday -> {
                        for (year in startDate.year..endDate.year) {
                            if (definition.monthDay.isValidYear(year)) {
                                val date = definition.monthDay.atYear(year)
                                if (date in startDate..endDate) add(date)
                            }
                        }
                    }

                    is SpecificDateHoliday -> {
                        if (definition.date in startDate..endDate) add(definition.date)
                    }
                }
            }
        }
    }
}
