package cz.kavan.radek.agent.bitcoin.mapper.impl;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;

public class AccountBalanceMapper {

    private AccountBalanceMapper() {
    }

    public static void mapAccountBalanceResponse(AccountBalance accountBalance, AccountBalanceEntity target) {

        target.setBtcAvailable(accountBalance.getBtcAvailable());
        target.setUsdAvailable(accountBalance.getUsdAvailable());
        target.setUsdBalance(accountBalance.getUsdBalance());
        target.setBtcBalance(accountBalance.getBtcBalance());
        target.setUsdReserved(accountBalance.getUsdReserved());
        target.setBtcReserved(accountBalance.getBtcReserved());
        target.setFee(accountBalance.getFee());

    }

}
