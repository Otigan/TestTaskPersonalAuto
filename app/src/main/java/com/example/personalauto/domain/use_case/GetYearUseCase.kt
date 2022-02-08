package com.example.personalauto.domain.use_case

import com.example.personalauto.domain.repository.YearRepository
import javax.inject.Inject

class GetYearUseCase @Inject constructor(private val yearRepository: YearRepository) {

    operator fun invoke(id: Int, name: String) = yearRepository.getYear(id, name)

}