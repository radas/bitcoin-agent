package cz.kavan.radek.agent.bitcoin.domain.dao;

import java.util.List;

import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;

public interface AccountBalanceDAO {

    void addAccountBalance(AccountBalanceEntity balance);

    List<AccountBalanceEntity> getBalance();

}
