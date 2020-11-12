package guru.springframework.sfgpetclinic.bootstrap;

import guru.springframework.sfgpetclinic.model.*;
import guru.springframework.sfgpetclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialityService specialityService;
    private final VisitService visitService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService,
                      SpecialityService specialityService, VisitService visitService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialityService = specialityService;
        this.visitService = visitService;
    }

    @Override
    public void run(String... args) throws Exception {

        if (petTypeService.findAll().isEmpty()) {
            loadData();
        }
    }

    private void loadData() {
        PetType petType1 = new PetType();
        petType1.setName("dog");
        PetType savedPetType1 = petTypeService.save(petType1);

        PetType petType2 = new PetType();
        petType2.setName("cat");
        PetType savedPetType2 = petTypeService.save(petType2);

        System.out.println("Loaded Pet Types...");

        Speciality surgery = new Speciality();
        surgery.setDescription("Surgery");

        Speciality denstistry = new Speciality();
        denstistry.setDescription("Dentistry");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");

        Speciality savedSurgery = specialityService.save(surgery);
        Speciality savedDentistry = specialityService.save(denstistry);
        Speciality savedRadiology = specialityService.save(radiology);

        System.out.println("Loading specialities..");

        Owner owner1 = new Owner();
        owner1.setFirstName("Michael");
        owner1.setLastName("Weston");
        owner1.setAddress("123 rue des chiens");
        owner1.setCity("Lyon");
        owner1.setTelephone("0836656565");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedPetType1);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setOwner(owner1);
        mikesPet.setName("Woof");

        Pet mikesPet2 = new Pet();
        mikesPet2.setPetType(savedPetType2);
        mikesPet2.setBirthDate(LocalDate.now());
        mikesPet2.setOwner(owner1);
        mikesPet2.setName("Miaou");

        owner1.getPets().addAll(List.of(mikesPet, mikesPet2));

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setFirstName("Fiona");
        owner2.setLastName("Glenanne");
        owner2.setAddress("555 rue des chats");
        owner2.setCity("Lyon");
        owner2.setTelephone("3615");

        Pet fionasPet = new Pet();
        fionasPet.setPetType(savedPetType2);
        fionasPet.setBirthDate(LocalDate.now().minusMonths(1));
        fionasPet.setOwner(owner2);
        fionasPet.setName("Waf");

        owner2.getPets().add(fionasPet);

        ownerService.save(owner2);

        System.out.println("Loaded owners...");

        Visit catVisit = new Visit();
        catVisit.setPet(fionasPet);
        catVisit.setDate(LocalDate.now());
        catVisit.setDescription("Sneezy Kitty");
        visitService.save(catVisit);

        Vet vet1 = new Vet();
        vet1.setFirstName("Sam");
        vet1.setLastName("Axe");
        vet1.getSpecialities().add(savedSurgery);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Jessie");
        vet2.setLastName("Porter");

        vet2.getSpecialities().addAll(List.of(savedRadiology, savedDentistry));

        vetService.save(vet2);

        System.out.println("Loaded Vets..");
    }
}
