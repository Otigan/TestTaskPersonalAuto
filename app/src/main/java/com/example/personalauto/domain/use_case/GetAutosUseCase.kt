package com.example.personalauto.domain.use_case

import com.example.personalauto.domain.repository.AutoRepository
import javax.inject.Inject

class GetAutosUseCase @Inject constructor(private val autoRepository: AutoRepository) {

    operator fun invoke(id: Int) = autoRepository.getAutos(id)

}