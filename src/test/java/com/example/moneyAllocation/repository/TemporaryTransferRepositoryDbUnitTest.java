package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import com.example.moneyAllocation.MoneyAllocationApplication;
import com.example.moneyAllocation.domain.TemporaryTransfer;
import com.example.moneyAllocation.domain.TemporaryTransferSelector;
import com.example.moneyAllocation.repository.util.DbTestExecutionListener;
import com.example.moneyAllocation.repository.util.TestDomainDataCreator;
import com.example.moneyAllocation.util.DbUnitUtil;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class TemporaryTransferRepositoryDbUnitTest {
    private final String DATA_DIR = this.getClass().getResource("")
            .getFile() + "data" + File.separator;

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class FindDbTest {
        @Autowired
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource dataSource;

        @Test
        public void testFindAll() {
            TemporaryTransferSelector selector = new TemporaryTransferSelector();
            List<TemporaryTransfer> temporaryTransferList = repository.find(selector);
            assertEquals(3, temporaryTransferList.size());
            assertEquals(1L, temporaryTransferList.get(0).getId());
            assertEquals(1L, temporaryTransferList.get(0).getFromAccount());
            assertEquals(2L, temporaryTransferList.get(0).getToAccount());
            assertEquals("desc1", temporaryTransferList.get(0).getDescription());
            assertEquals(4800, temporaryTransferList.get(0).getAmount());
            assertEquals(1L, temporaryTransferList.get(0).getUserId());
            assertEquals(2L, temporaryTransferList.get(1).getId());
            assertEquals(3L, temporaryTransferList.get(2).getId());
        }

        @Test
        public void testFindWithUserId() {
            TemporaryTransferSelector selector = new TemporaryTransferSelector();
            selector.setUserId(1L);
            List<TemporaryTransfer> temporaryTransferList = repository.find(selector);
            assertEquals(2, temporaryTransferList.size());
            assertEquals(1L, temporaryTransferList.get(0).getId());
            assertEquals(1L, temporaryTransferList.get(0).getFromAccount());
            assertEquals(2L, temporaryTransferList.get(0).getToAccount());
            assertEquals("desc1", temporaryTransferList.get(0).getDescription());
            assertEquals(4800, temporaryTransferList.get(0).getAmount());
            assertEquals(1L, temporaryTransferList.get(0).getUserId());
            assertEquals(2L, temporaryTransferList.get(1).getId());
        }
    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class InsertDbTest {

        @Autowired
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File insertExpectedData  = new File(DATA_DIR + "temporary_insert_expected.xlsx");

        @Test
        public void testInsert() {
            TemporaryTransfer temporaryTransfer = TestDomainDataCreator.temporaryCreate(
                    4L, 4L, 5L, 3500, "desc4", 2L);
            repository.add(temporaryTransfer);
            DbUnitUtil.assertMutateResult(
                    source, "TEMPORARY_TRANSFER",
                    insertExpectedData, Arrays.asList());
        }

    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class UpdateDbTest {

        @Autowired
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File updateExpectedData  = new File(DATA_DIR + "temporary_update_expected.xlsx");

        @Test
        public void testUpdate() {
            TemporaryTransfer temporaryTransfer = TestDomainDataCreator.temporaryCreate(
                    3L, 4L, 5L, 3000, "desc3", 2L);
            repository.set(temporaryTransfer);
            DbUnitUtil.assertMutateResult(
                    source, "TEMPORARY_TRANSFER",
                    updateExpectedData, Arrays.asList());
        }

    }

    @SpringBootTest(classes = MoneyAllocationApplication.class)
    @TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbTestExecutionListener.class})
    @Nested
    public class DeleteDbTest {

        @Autowired
        private TemporaryTransferRepository repository;
        @Autowired
        private DataSource source;

        private final File deleteExpectedData  = new File(DATA_DIR + "temporary_delete_expected.xlsx");

        @Test
        public void testDelete() {
            repository.delete(3L);
            DbUnitUtil.assertMutateResult(
                    source, "TEMPORARY_TRANSFER",
                    deleteExpectedData, Arrays.asList());
        }

    }

}