package com.example.moneyAllocation.repository;

import com.example.moneyAllocation.domain.Account;
import com.example.moneyAllocation.domain.AccountSelector;
import com.example.moneyAllocation.exception.ResourceNotFoundException;
import com.example.moneyAllocation.repository.mybatis.AccountMapper;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private final SqlSession sqlSession;
    private final RegularTransferRepository regularTransferRepository;
    private final TemporaryTransferRepository temporaryTransferRepository;

    public AccountRepositoryImpl(SqlSession sqlSession, RegularTransferRepository regularTransferRepository,
                                 TemporaryTransferRepository temporaryTransferRepository) {
        this.sqlSession = sqlSession;
        this.regularTransferRepository = regularTransferRepository;
        this.temporaryTransferRepository = temporaryTransferRepository;
    }

    @Override
    public List<Account> find(Long ownerId) {
        return this.sqlSession.getMapper(AccountMapper.class).find(ownerId);
    }

    @Override
    public Account findOne(AccountSelector selector) {
        Account account = this.sqlSession.getMapper(AccountMapper.class).findOne(selector);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found");
        }
        return account;
    }

    @Override
    public void add(Account account) {
        int affected = this.sqlSession.getMapper(AccountMapper.class).add(account);
        if (affected != 1) {
            throw new RuntimeException("データの追加に失敗しました．");
        }
    }

    @Override
    public void set(Account account) {
        int affected = this.sqlSession.getMapper(AccountMapper.class).set(account);
        if (affected != 1) {
            throw new ResourceNotFoundException("Account not found");
        }
    }

    @Override
    public void delete(AccountSelector accountSelector) {
        regularTransferRepository.setNullAccount(accountSelector.getId());
        temporaryTransferRepository.setNullAccount(accountSelector.getId());

        int affected = this.sqlSession.getMapper(AccountMapper.class).delete(accountSelector);
        if (affected != 1) {
            throw new ResourceNotFoundException("Account not found");
        }
    }
}
