package hello.golong.domain.donation.application;


import org.springframework.stereotype.Service;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.*;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class SmartContractService {
/*
    public boolean compareWalletAddresses(String address1, String address2) {
        String lowerAddress1 = address1.toLowerCase();
        String lowerAddress2 = address2.toLowerCase();

        return lowerAddress1.equals(lowerAddress2);
    }*/

    private String contractAddress = "0xb248d4cbb7c728ef0e81de0b4b17734aeed2d280";
    private String nodeUrl = "https://sepolia.infura.io/v3/ca7db5c20c11412084523bb7cbb7289d";
    private Web3j web3j = Web3j.build(new HttpService(nodeUrl));


    public String transfer(String postAddress, String memberAddress, String privateKey, Long amount) throws IOException, TransactionException {
        long TX_END_CHECK_DURATION = 5000;
        int TX_END_CHECK_RETRY = 3;
        long CHAIN_ID = 11155111;//sepolia chain id

        Credentials credential = Credentials.create(privateKey);


        //insufficient balance error 확인 코드
        EthGetBalance balance = web3j.ethGetBalance(credential.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger accountBalance = balance.getBalance();
        System.out.println("accountBalance = " + accountBalance.toString());

        List<Type> params = new ArrayList<>();
        params.add(new Address(postAddress));
        params.add(new Uint256(new BigInteger(String.valueOf(amount))));

        List<TypeReference<?>> returnTypes = Collections.<TypeReference<?>>emptyList();

        Function function = new Function(
                "transfer",
                params,
                returnTypes
        );

        String txData = FunctionEncoder.encode(function);

        TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(web3j, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY);
        TransactionManager manager = new RawTransactionManager(web3j, credential, CHAIN_ID, receiptProcessor);
        ContractGasProvider gasProvider = new DefaultGasProvider();

        String txHash = manager.sendTransaction(
                gasProvider.getGasPrice("transfer"),
                gasProvider.getGasLimit("transfer"),
                contractAddress,
                txData,
                BigInteger.ZERO
        ).getTransactionHash();

        TransactionReceipt receipt = receiptProcessor.waitForTransactionReceipt(txHash);
        System.out.println("Status = " + receipt.getStatus());
        System.out.println("TransactionHash = " + receipt.getTransactionHash());

        return receipt.getTransactionHash();



    }




/*
    public String transfer(String postAddress, String memberAddress, String privateKey, Long amount) {

        if (amount <= 0) return "amount exception";

        try {
            Web3ClientVersion web3ClientVersion = web3j.web3ClientVersion().send();
            String clientVersion = web3ClientVersion.getWeb3ClientVersion();
            System.out.println("Connected to Infura. Client version: " + clientVersion);
        } catch (Exception e) {
            System.out.println("Failed to connect to Infura: " + e.getMessage());
        }


        //TODO : 051810 함수 반환타입 수정해봄
        Function function = new Function(
                "transfer",  // 호출할 함수의 이름
                Arrays.asList(new Address(postAddress), new Uint256(BigInteger.valueOf(amount))),//매개변수를 리스트로 전달
                //Arrays.asList(new TypeReference<Bool>() {
                //}));//함수 반환타입 지정
                Collections.emptyList());

        System.out.println("function = " + function.toString());
        try {
            //
            String txHash = this.ethSendTransaction(function, memberAddress, privateKey);
            System.out.println("txHash = " + txHash);
            return txHash;

        } catch (Exception e) { //TODO : 예외처리 관리하기
            e.printStackTrace();
        }
        return "tmp";

    }




    public String ethSendTransaction(Function function, String memberAddress, String privateKey)
            throws IOException, InterruptedException, ExecutionException {

        Admin web3jAdmin = Admin.build(new HttpService(nodeUrl));
        PersonalUnlockAccount personalUnlockAccount = web3jAdmin.personalUnlockAccount(memberAddress, privateKey).send();

        //TODO : NPE 발생
        if (personalUnlockAccount.accountUnlocked()) {
            // send a transaction

            EthGetTransactionCount ethGetTransactionCount = web3j.ethGetTransactionCount(
                    memberAddress, DefaultBlockParameterName.LATEST).sendAsync().get();

            BigInteger nonce = ethGetTransactionCount.getTransactionCount();


            //TODO : GasLimit 수정하기
            Transaction transaction = Transaction.createFunctionCallTransaction(memberAddress, nonce,
                    Transaction.DEFAULT_GAS,
                    BigInteger.valueOf(3000000000L), contractAddress,
                    FunctionEncoder.encode(function));

            EthSendTransaction ethSendTransaction = web3j.ethSendTransaction(transaction).send();

            // transaction에 대한 transaction Hash값 얻기.
            String transactionHash = ethSendTransaction.getTransactionHash();

            Thread.sleep(5000);

            return transactionHash;
        }
        //TODO : 예외처리
        else return "check ethereum personal Lock";
    }*/

}

