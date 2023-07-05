package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.RegularTransfer;
import com.example.moneyAllocation.domain.RegularTransferSelector;
import com.example.moneyAllocation.repository.mybatis.RegularTransferMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class RegularTransferRepositoryImpl implements RegularTransferRepository{
    private final SqlSession sqlSession;

    public RegularTransferRepositoryImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<RegularTransfer> find(RegularTransferSelector selector) {
        return this.sqlSession.getMapper(RegularTransferMapper.class).find(selector);
    }

    @Override
    public RegularTransfer findOne(Long id) {
        RegularTransfer regularTransfer = this.sqlSession.getMapper(RegularTransferMapper.class).findOne(id);
        if (regularTransfer == null) {
            throw new ResourceNotFoundException("RegularTransfer not found");
        }
        return  regularTransfer;
    }

    @Override
    public void add(RegularTransfer regularTransfer) {
        int affected = this.sqlSession.getMapper(RegularTransferMapper.class).add(regularTransfer);
        if (affected != 1) {
            throw new RuntimeException("データの追加に失敗しました．");
        }

    }

    @Override
    public void set(RegularTransfer regularTransfer) {
        int affected = this.sqlSession.getMapper(RegularTransferMapper.class).set(regularTransfer);
        if (affected != 1) {
            throw new ResourceNotFoundException("RegularTransfer not found");
        }
    }

    @Override
    public void delete(Long id) {
        int affected = this.sqlSession.getMapper(RegularTransferMapper.class).delete(id);
        if (affected != 1) {
            throw new ResourceNotFoundException("RegularTransfer not found");
        }
    }
}
