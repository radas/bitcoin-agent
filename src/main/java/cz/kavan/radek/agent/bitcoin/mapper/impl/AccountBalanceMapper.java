package cz.kavan.radek.agent.bitcoin.mapper.impl;

import cz.kavan.radek.agent.bitcoin.domain.AccountBalance;
import cz.kavan.radek.agent.bitcoin.domain.entity.AccountBalanceEntity;

public class AccountBalanceMapper {

    public static void mapAccountBalanceResponse(AccountBalance accountBalance, AccountBalanceEntity target) {

        target.setBtc_available(accountBalance.getBtc_available());
        target.setUsd_available(accountBalance.getUsd_available());
        target.setUsdBalance(accountBalance.getUsd_balance());
        target.setBtcBalance(accountBalance.getBtc_balance());
        target.setUsdReserved(accountBalance.getUsd_reserved());
        target.setBtcReserved(accountBalance.getBtc_reserved());
        target.setFee(accountBalance.getFee());

    }

}
