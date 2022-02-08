package com.example.personalauto.domain.use_case

import com.example.personalauto.domain.repository.ManufacturerRepository
import javax.inject.Inject

class GetManufacturersUseCase @Inject constructor(private val manufacturerRepository: ManufacturerRepository) {

    operator fun invoke() = manufacturerRepository.getManufacturers()

}