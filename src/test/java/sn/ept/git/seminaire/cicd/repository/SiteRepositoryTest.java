package sn.ept.git.seminaire.cicd.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import sn.ept.git.seminaire.cicd.data.SiteDTOTestData;
import sn.ept.git.seminaire.cicd.dto.SiteDTO;
import sn.ept.git.seminaire.cicd.mappers.SiteMapper;
import sn.ept.git.seminaire.cicd.models.Site;
import sn.ept.git.seminaire.cicd.repositories.SiteRepository;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class SiteRepositoryTest extends RepositoryBaseTest {

    @Autowired
    private SiteMapper mapper;
    @Autowired
    private SiteRepository repository;

    static SiteDTO dto;
    Site entity;
    Optional<Site> optionalSite;

    @BeforeAll
    static void beforeAll(){
        dto = SiteDTOTestData.defaultDTO();
    }

    @BeforeEach
    void setUp() {
        entity = mapper.asEntity(dto);
        repository.deleteAll();
        entity = repository.saveAndFlush(entity);
    }

    @Test
    void findByName_shouldRetrunResult() {
        optionalSite = repository.findByName(entity.getName());
        assertThat(optionalSite)
                .isNotNull()
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(entity);
    }

    @Test
    void findByName_withBadName_shouldReturnNotFound() {
        optionalSite = repository.findByName(UUID.randomUUID().toString());
        assertThat(optionalSite)
                .isNotNull()
                .isNotPresent();
    }

    @Test
    void findByName_afterDelete_shouldReturnNotFound() {
        entity.setDeleted(true);
        entity = repository.saveAndFlush(entity);
        optionalSite = repository.findByName(entity.getName());
        assertThat(optionalSite)
                .isNotNull()
                .isNotPresent();
    }

//    @Test
//    void findByEmail_shouldReturnResult() {
//        optionalSite = repository.findByEmail(entity.getEmail());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isPresent()
//                .get()
//                .usingRecursiveComparison()
//                .isEqualTo(entity);
//    }
//
//    @Test
//    void findByEmail_withBadEmail_shouldReturnNotFound() {
//        optionalSite = repository.findByEmail(UUID.randomUUID().toString());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isNotPresent();
//    }
//
//
//    @Test
//    void findByPhone_shouldReturnResult() {
//        optionalSite = repository.findByPhone(entity.getPhone());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isPresent()
//                .get()
//                .usingRecursiveComparison()
//                .isEqualTo(entity);
//    }
//
//    @Test
//    void findByPhone_withBadPhone_shouldReturnResult() {
//        optionalSite = repository.findByPhone(UUID.randomUUID().toString());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isNotPresent();
//    }
//
//
//    @Test
//    void findByNameWithIdNotEqual_shouldReturnResult() {
//        optionalSite = repository.findByNameWithIdNotEqual(entity.getName(),UUID.randomUUID());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isPresent()
//                .get()
//                .usingRecursiveComparison()
//                .isEqualTo(entity);
//    }
//
//    @Test
//    void  findByNameWithIdNotEqual_withSameId_shouldReturnNoResult () {
//        optionalSite = repository.findByNameWithIdNotEqual(entity.getName(),entity.getId());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isNotPresent();
//    }
//
//
//
//
//    @Test
//    void findByPhoneWithIdNotEqual_shouldReturnResult() {
//        optionalSite = repository.findByPhoneWithIdNotEqual(entity.getPhone(),UUID.randomUUID());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isPresent()
//                .get()
//                .usingRecursiveComparison()
//                .isEqualTo(entity);
//    }
//
//    @Test
//    void findByPhoneWithIdNotEqual_withSameId_shouldReturnNoResult() {
//        optionalSite = repository.findByPhoneWithIdNotEqual(entity.getPhone(),entity.getId());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isNotPresent();
//    }
//
//
//    @Test
//    void findByEmailWithIdNotEqual_shouldReturnResult() {
//        optionalSite = repository.findByEmailWithIdNotEqual(entity.getEmail(),UUID.randomUUID());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isPresent()
//                .get()
//                .usingRecursiveComparison()
//                .isEqualTo(entity);
//    }
//
//    @Test
//    void findByEmailWithIdNotEqual_withSameId_shouldReturnNoResult() {
//        optionalSite = repository.findByEmailWithIdNotEqual(entity.getEmail(),entity.getId());
//        assertThat(optionalSite)
//                .isNotNull()
//                .isNotPresent();
//    }

}