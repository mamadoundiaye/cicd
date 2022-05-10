package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.SocieteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SocieteDTO;
import sn.ept.git.seminaire.cicd.mappers.SocieteMapper;
import sn.ept.git.seminaire.cicd.models.Societe;
import sn.ept.git.seminaire.cicd.repositories.SocieteRepository;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SocieteRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private SocieteMapper mapper;
    @Autowired
    private SocieteRepository repository;

    static SocieteDTO dto;
    Societe entity;
    Optional<Societe> optionalSociete;

    @BeforeAll
    static void beforeAll(){
        dto = SocieteDTOTestData.defaultDTO();
    }

    @BeforeEach
    void setUp() {
        entity = mapper.asEntity(dto);
        repository.deleteAll();
        entity = repository.saveAndFlush(entity);
    }

    @Test
    void findByName_shouldRetrunResult() {
        optionalSociete = repository.findByName(entity.getName());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByName_withBadName_shouldReturnNotFound() {
        optionalSociete = repository.findByName(UUID.randomUUID().toString());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void findByName_afterDelete_shouldReturnNotFound() {
        entity.setDeleted(true);
        entity = repository.saveAndFlush(entity);
        optionalSociete = repository.findByName(entity.getName());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void findByEmail_shouldReturnResult() {
        optionalSociete = repository.findByEmail(entity.getEmail());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByEmail_withBadEmail_shouldReturnNotFound() {
        optionalSociete = repository.findByEmail(UUID.randomUUID().toString());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    void findByPhone_shouldReturnResult() {
        optionalSociete = repository.findByPhone(entity.getPhone());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByPhone_withBadPhone_shouldReturnResult() {
        optionalSociete = repository.findByPhone(UUID.randomUUID().toString());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    void findByNameWithIdNotEqual_shouldReturnResult() {
        optionalSociete = repository.findByNameWithIdNotEqual(entity.getName(),UUID.randomUUID());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void  findByNameWithIdNotEqual_withSameId_shouldReturnNoResult () {
        optionalSociete = repository.findByNameWithIdNotEqual(entity.getName(),entity.getId());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }




    @Test
    void findByPhoneWithIdNotEqual_shouldReturnResult() {
        optionalSociete = repository.findByPhoneWithIdNotEqual(entity.getPhone(),UUID.randomUUID());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByPhoneWithIdNotEqual_withSameId_shouldReturnNoResult() {
        optionalSociete = repository.findByPhoneWithIdNotEqual(entity.getPhone(),entity.getId());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }


    @Test
    void findByEmailWithIdNotEqual_shouldReturnResult() {
        optionalSociete = repository.findByEmailWithIdNotEqual(entity.getEmail(),UUID.randomUUID());
        assertThat(optionalSociete)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByEmailWithIdNotEqual_withSameId_shouldReturnNoResult() {
        optionalSociete = repository.findByEmailWithIdNotEqual(entity.getEmail(),entity.getId());
        assertThat(optionalSociete)
                .isNotNull()
                .isNotPresent();
    }

}