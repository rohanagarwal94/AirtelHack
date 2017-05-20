package com.hack.rohanagarwal94.airtelhack.api;

import com.hack.rohanagarwal94.airtelhack.model.BalanceEnquiryResponse;
import com.hack.rohanagarwal94.airtelhack.model.FundTransferResponse;
import com.hack.rohanagarwal94.airtelhack.model.RecentTransactionResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransferService {

    @GET("balanceenquiry")
    Call<List<BalanceEnquiryResponse>> balanceEnquiry(@Query("client_id") String clientId, @Query("token") String token, @Query("accountno") String accountNo);

//    https://retailbanking.mybluemix.net/banking/icicibank/balanceenquiry?client_id=ICICI .APPATHON@GMAIL.COM&token=656b872f26e4&accountno=4444777755550001

//    Sample Response:
//            [{"code":200}, {"balance":"462510.8188610320", "accountno":"4444777755550001", "accounttype":"Savings Account", "balancetime":"23-03-17 14:25:160 IST" }]

    @GET("fundTransfer")
    Call<List<FundTransferResponse>> fundTransfer(@Query("client_id") String clientId, @Query("token") String token, @Query("srcAccount") String srcAccount, @Query("destAccount") String destAccount, @Query("amt") String amount, @Query("payeeDesc") String payeeDesc, @Query("payeeId") int id, @Query("type_of_transaction") String type);

//    Sample Request:
//    https://retailbanking.mybluemix.net/banking/icicibank/fundTransfer?client_id=ICICI.APPATHON@GMAIL.COM&token=656b872f26e4&srcAccount=4444777755550001&destAccount=4444777755550002&amt=200&payeeDesc=NA&payeeId=1&type_of_transaction=school fee payment

//    Sample Response:
//            [{"code":200}, { "destination_accountno":"4444777755550002", "transaction_date":"2017-03-23 15:38:38", "referance_no":"171", "transaction_amount":"200.00", "payee_name":"A2", "payee_id":"1", "status":"SUCCESS" }]

    @GET("recenttransaction")
    Call<List<RecentTransactionResponse>> recentTransaction(@Query("client_id") String clientId, @Query("token") String token, @Query("srcAccount") String srcAccount);

//    Sample Request:
//    https://retailbanking.mybluemix.net/banking/icicibank/recenttransaction?client_id=ICICI.APPATHON@GMAIL.COM&token=656b872f26e4&accountno=4444777755550001

//    Sample Response:
//            [{"code":200}, {"transactiondate":"2017-03-22 16:56:03", "closing_balance":"462510.81886103250"
//        "accountno":"4444777755550001", "credit_debit_flag":"Dr.", "transaction_amount":"0.166030217499584940", "remark":"purchase product"}]
//

}
