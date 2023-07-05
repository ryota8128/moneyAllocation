package com.example.moneyAllocation.repository;

import static org.junit.jupiter.api.Assertions.*;
import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.mybatis.RegularTransferMapper;
import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class RegularTransferRepositoryImplTest {
    @Mock
    SqlSession sqlSession;
    @Mock
    RegularTransferMapper mapper;

    private AutoCloseable mocks;

    private RegularTransferRepository repository;

    @BeforeEach
    public void before() {
        mocks = MockitoAnnotations.openMocks(this);
        Mockito.doReturn(mapper).when(sqlSession).getMapper(RegularTransferMapper.class);
        repository = new RegularTransferRepositoryImpl(sqlSession);
    }

    @AfterEach
    public void after() {
        Mockito.verify(sqlSession, Mockito.times(1)).getMapper(RegularTransferMapper.class);
    }

    @Test
    void find() {
        List<RegularTransfer> regularTransferList = new ArrayList<>();
        regularTransferList.add(new RegularTransfer());

        RegularTransferSelector selector = new RegularTransferSelector();
        selector.setUserId(1L);
        Mockito.doReturn(regularTransferList).when(mapper).find(selector);
        List<RegularTransfer> result = repository.find(selector);
        assertEquals(regularTransferList, result);
        Mockito.verify(mapper, Mockito.times(1)).find(selector);
    }

    @Test
    void findOne() {
        Long id = 1L;
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(regularTransfer).when(mapper).findOne(id);
        RegularTransfer result = repository.findOne(id);
        assertEquals(regularTransfer, result);
        Mockito.verify(mapper, Mockito.times(1)).findOne(id);
    }

    @Test
    void add() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(1).when(mapper).add(regularTransfer);
        repository.add(regularTransfer);
        Mockito.verify(mapper, Mockito.times(1)).add(regularTransfer);
    }

    @Test
    void addFail() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(0).when(mapper).add(regularTransfer);

        assertThrows(RuntimeException.class, () -> repository.add(regularTransfer));
    }

    @Test
    void set() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(1).when(mapper).set(regularTransfer);
        repository.set(regularTransfer);
        Mockito.verify(mapper, Mockito.times(1)).set(regularTransfer);
    }

    @Test
    void setFail() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Mockito.doReturn(0).when(mapper).set(regularTransfer);

        assertThrows(RuntimeException.class, () -> repository.set(regularTransfer));
    }

    @Test
    void delete() {
        RegularTransfer regularTransfer = new RegularTransfer();
        Long id = 1L;
        Mockito.doReturn(1).when(mapper).delete(id);
        repository.delete(id);
        Mockito.verify(mapper, Mockito.times(1)).delete(id);
    }

    @Test
    void deleteFail() {
        Long id = 1L;
        Mockito.doReturn(0).when(mapper).delete(id);

        assertThrows(RuntimeException.class, () -> repository.delete(id));
    }
}