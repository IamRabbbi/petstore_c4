package com.petstore.service.pet;

import com.petstore.data.model.Pet;
import com.petstore.data.repository.PetRepository;
import com.petstore.web.exceptions.PetDoesNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetServiceImpl implements PetService {


    @Autowired
    PetRepository petRepository;

    @Override
    public Pet savePet(Pet pet) {

        if(pet == null){
            throw new NullPointerException("Pet Object cannot be null");
        }

        return petRepository.save(pet);
    }

    @Override
    public Pet updatePet(Pet pet) throws PetDoesNotExistException {
        Pet savedPet = petRepository.findById(pet.getId()).orElse(null);
        if(savedPet == null){
            throw new PetDoesNotExistException("Pet with id:"+ pet.getId()+" does not exist");
        }
        else{
            if(pet.getAge() != null){
                savedPet.setAge(pet.getAge());
            }
            if(pet.getBreed() != null){
                savedPet.setBreed(pet.getBreed());
            }
            if(pet.getColor() != null){
                savedPet.setColor(pet.getColor());
            }
            if(pet.getPetSex() != null){
                savedPet.setPetSex(pet.getPetSex());
            }
            if(pet.getName() != null){
                savedPet.setName(pet.getName());
            }
           return petRepository.save(savedPet);
        }
    }

    @Override
    public Pet findPetById(Integer id) throws PetDoesNotExistException {

        Pet savedPet = petRepository.findById(id).orElse(null);
        //check that pet exists
        if(savedPet != null){
            return savedPet;
        }
        else{
            throw new PetDoesNotExistException("Pet with the Id:"+id+"Does not " +
                    "Exist");
        }

    }

    @Override
    public List<Pet> findAllPets() {
        return petRepository.findAll();
    }

    @Override
    public void deletePetById(Integer id) throws PetDoesNotExistException {

        try {
            petRepository.deleteById(id);
        }catch (Exception ex){
            throw new PetDoesNotExistException("\"Pet with the Id:\"+id+\" Does not \" +\n" +
                    "                    \"Exist\"");
        }

    }
}
