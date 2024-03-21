package ru.otus.bank.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.bank.entity.Account;
import ru.otus.bank.entity.Agreement;
import ru.otus.bank.service.AccountService;
import ru.otus.bank.service.exception.AccountException;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentProcessorImplTest {

    @Mock
    AccountService accountService;

    @InjectMocks
    PaymentProcessorImpl paymentProcessor;

    @Test
    public void testTransfer() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);

        when(accountService.getAccounts(argThat(argument -> argument != null && argument.getId() == 1L)))
                .thenReturn(List.of(sourceAccount));

        when(accountService.getAccounts(argThat(argument -> argument != null && argument.getId() == 2L)))
                .thenReturn(List.of(destinationAccount));

        paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement,
                0, 0, BigDecimal.ONE);

    }

    @ParameterizedTest
    @CsvSource(value = {"0|1|2","1|0|1"}, delimiter = '|')
    public void transferAccountNotTransferTest(
            Integer sourceType,
            Integer destinationType,
            Integer accountInteractions
    ) {
        //given
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);

        //when
        when(accountService.getAccounts(sourceAgreement)).thenReturn(List.of(sourceAccount));
        Mockito.lenient().when(accountService.getAccounts(destinationAgreement)).thenReturn(List.of(destinationAccount));

        //then
        assertThatThrownBy(() -> paymentProcessor.makeTransfer(sourceAgreement, destinationAgreement, sourceType,
                destinationType, BigDecimal.ONE)).isInstanceOf(AccountException.class);
        verify(accountService, times(accountInteractions)).getAccounts(any());
        verifyNoMoreInteractions(accountService);
    }

    @Test
    public void makeTransferWithComissioin() {
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);

        when(accountService.getAccounts(argThat(argument -> argument != null && argument.getId() == 1L)))
                .thenReturn(List.of(sourceAccount));

        when(accountService.getAccounts(argThat(argument -> argument != null && argument.getId() == 2L)))
                .thenReturn(List.of(destinationAccount));

        paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement,
                0, 0, BigDecimal.ONE, BigDecimal.TEN);

    }

    @ParameterizedTest
    @CsvSource(value = {"0|1|2","1|0|1"}, delimiter = '|')
    public void makeTransferWithCommisionExceptionTest(
            Integer sourceType,
            Integer destinationType,
            Integer accountInteractions
    ) {
        //given
        Agreement sourceAgreement = new Agreement();
        sourceAgreement.setId(1L);

        Agreement destinationAgreement = new Agreement();
        destinationAgreement.setId(2L);

        Account sourceAccount = new Account();
        sourceAccount.setAmount(BigDecimal.TEN);
        sourceAccount.setType(0);

        Account destinationAccount = new Account();
        destinationAccount.setAmount(BigDecimal.ZERO);
        destinationAccount.setType(0);

        //when
        when(accountService.getAccounts(sourceAgreement)).thenReturn(List.of(sourceAccount));
        Mockito.lenient().when(accountService.getAccounts(destinationAgreement)).thenReturn(List.of(destinationAccount));

        //then
        assertThatThrownBy(() -> paymentProcessor.makeTransferWithComission(sourceAgreement, destinationAgreement, sourceType,
                destinationType, BigDecimal.ONE, BigDecimal.TEN)).isInstanceOf(AccountException.class);
        verify(accountService, times(accountInteractions)).getAccounts(any());
        verifyNoMoreInteractions(accountService);
    }
}
