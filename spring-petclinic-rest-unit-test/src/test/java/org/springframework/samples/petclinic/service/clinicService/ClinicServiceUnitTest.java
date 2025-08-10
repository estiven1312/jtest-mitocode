package org.springframework.samples.petclinic.service.clinicService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.*;
import org.springframework.samples.petclinic.service.ClinicServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClinicServiceUnitTest {
    @Mock
    PetRepository petRepository;
    @Mock
    VetRepository vetRepository;
    @Mock
    OwnerRepository ownerRepository;
    @Mock
    VisitRepository visitRepository;
    @Mock
    SpecialtyRepository specialtyRepository;
    @Mock
    PetTypeRepository petTypeRepository;
    @InjectMocks
    ClinicServiceImpl clinicService;
    @Test
    @DisplayName("should find all pets")
    void shouldFindAllPets() {
        // Given
        List<Pet> pets = List.of(new Pet(), new Pet());
        when(petRepository.findAll()).thenReturn(pets);

        // When
        List<Pet> foundPets = (List<Pet>) clinicService.findAllPets();

        // Then
        assertEquals(2, foundPets.size());
    }

    @Test
    @DisplayName("should delete pet")
    void shouldDeletePet() {

        // When
        clinicService.deletePet(new Pet());
        // Then
        verify(petRepository).delete(any(Pet.class));
    }

    @Test
    @DisplayName("should throws DataAccessException when try delete pet")
    void shouldThrowsDataAccessExceptionWhenTryDeletePet() {
        // Given
        doThrow(new DataAccessException("Message") {}).when(petRepository).delete(any(Pet.class));
        // When
        assertThrows(DataAccessException.class, () -> clinicService.deletePet(new Pet()));
        // Then
        verify(petRepository).delete(any(Pet.class));
    }

    @Test
    @DisplayName("should find owner by id")
    void shouldFindOwnerById() {
        Owner owner = new Owner();
        owner.setId(1);
        when(ownerRepository.findById(1)).thenReturn(owner);
        Owner foundOwner = clinicService.findOwnerById(1);
        assertEquals(1, foundOwner.getId());
    }

    @Test
    @DisplayName("should return null when no owner with given id")
    void shouldReturnNullWhenNoOwnerWithGivenId() {

        when(ownerRepository.findById(1)).thenThrow(EmptyResultDataAccessException.class);
        Owner foundOwner = clinicService.findOwnerById(1);
        assertNull( foundOwner);
    }

    @Test
    void shouldReturnVisitsByPetId() {
       // Given
        int petId = 42;
        Visit visit1 = new Visit();
        visit1.setId(1);
        Visit visit2 = new Visit();
        visit2.setId(2);
        List<Visit> mockVisits = Arrays.asList(visit1, visit2);
        when(visitRepository.findByPetId(petId)).thenReturn(mockVisits);

        // When

        Collection<Visit> result = clinicService.findVisitsByPetId(petId);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(visit1));
        assertTrue(result.contains(visit2));
        verify(visitRepository, times(1)).findByPetId(petId);
    }

}
