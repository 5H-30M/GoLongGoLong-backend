package hello.golong.domain.donation.application;


import hello.golong.domain.donation.domain.Donation;
import hello.golong.domain.donation.dto.DonationDto;
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
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class SmartContractService {
    private String nodeUrl = "https://sepolia.infura.io/v3/ca7db5c20c11412084523bb7cbb7289d";
    private Web3j web3j = Web3j.build(new HttpService(nodeUrl));

    //TODO : donationDto를 여기까지 끌고오는 것은 시스템 구조상 좋지 않은것 같긴한데... 그치만 transactionId랑 시간 둘다 반환할 수 없으니까..
    //TODO : 아니면 List<String> 이라도 사용하는게 나은가..?

    public TransactionReceipt transfer(String contractFunctionName, String privateKey, Long amount, String contractAddress, String toAddress) throws IOException, TransactionException {
        long TX_END_CHECK_DURATION = 20000;
        int TX_END_CHECK_RETRY = 3;
        long CHAIN_ID = 11155111;

        Credentials credential = Credentials.create(privateKey);

        //TODO : 여기 예외처리로 바꾸기 , 일단 어차피 isCorrectPrivateKey에서 Exceptino 처리해서 잘못된 개인키일경우 그 메소드에서 프로그램 종료됨
        //TODO : 에러 핸들링하기
        //if(!isCorrectPrivateKey(memberAddress, credential.getAddress())) return donationDto;


        //insufficient balance error 확인 코드
        EthGetBalance balance = web3j.ethGetBalance(credential.getAddress(), DefaultBlockParameterName.LATEST).send();
        BigInteger accountBalance = balance.getBalance();
        System.out.println("accountBalance = " + accountBalance.toString());

        List<Type> params = new ArrayList<>();
        params.add(new Address(toAddress));
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
                gasProvider.getGasPrice(contractFunctionName),
                gasProvider.getGasLimit(contractFunctionName),
                contractAddress,
                txData,
                BigInteger.ZERO
        ).getTransactionHash();


        TransactionReceipt receipt = receiptProcessor.waitForTransactionReceipt(txHash);
        System.out.println("Status = " + receipt.getStatus());
        System.out.println("TransactionHash = " + receipt.getTransactionHash());

        return receipt;
    }
    //String postAddress, String memberAddress, String privateKey, Long amount
    public DonationDto transferGOL(String postAddress, String memberAddress, DonationDto donationDto) throws IOException, TransactionException {

        String contractAddress = "0xb248d4cbb7c728ef0e81de0b4b17734aeed2d280";

        TransactionReceipt receipt = this.transfer("transfer", donationDto.getPrivateKey(), donationDto.getAmount(), contractAddress, postAddress);
        //EthBlock.Block block = web3.ethGetBlockByHash(blockHash, false).send().getBlock();
        EthBlock.Block block = web3j.ethGetBlockByHash(receipt.getBlockHash(), false).send().getBlock();

        long timestamp = block.getTimestamp().longValue();
        Date date = new Date(timestamp * 1000);
        System.out.println("created At = " + date);

        return setTransactionInformation(donationDto, receipt, date);

    }
    public void transferSEth(String memberAddress) throws TransactionException, IOException {
        String contractAddressEth = "0x12768cfaf05c1bae761b098842ba66b1520a1d06eb4fdb8508827893d20a789a";
        TransactionReceipt receipt = this.transfer("sendEther", "210532c54c56fe8f221d55d6d7b462ae3fa831f0059b49769450d8812f21d6fa", 1L, contractAddressEth, memberAddress);

        EthBlock.Block block = web3j.ethGetBlockByHash(receipt.getBlockHash(), false).send().getBlock();

        long timestamp = block.getTimestamp().longValue();
        Date date = new Date(timestamp * 1000);
        System.out.println("created At = " + date);

    }
    DonationDto setTransactionInformation(DonationDto donationDto, TransactionReceipt receipt, Date date) {
        donationDto.setTransactionId(receipt.getTransactionHash());
        donationDto.setTransactionCreatedAt(date);
        return donationDto;

    }

    public boolean isCorrectPrivateKey(String address, String addressByPrivateKey) {
        if(address.equalsIgnoreCase(addressByPrivateKey)) return true;
        else throw new IllegalArgumentException("개인키가 일치하지 않습니다");
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

