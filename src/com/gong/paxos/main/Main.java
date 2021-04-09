package com.gong.paxos.main;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.gong.paxos.doer.Acceptor;
import com.gong.paxos.doer.Proposer;

/**
 * 主函数
 *
 */
public class Main {
    private static final int NUM_OF_PROPOSER = 10;
    private static final int NUM_OF_ACCEPTOR = 200;
    //CountDownLatch是JDK提供的一个同步工具，它可以让一个或多个线程等待，一直等到其他线程中执行完成一组操作。底层基于AQS
	//设置与PROPOSER数量相同的CountDownLatch锁
    public static CountDownLatch latch = new CountDownLatch(NUM_OF_PROPOSER);


    public static void main(String[] args) {

        List<Acceptor> acceptors = new ArrayList<>();
        for (int i = 0; i < NUM_OF_ACCEPTOR; i++) {
            acceptors.add(new Acceptor());
        }

        //分配一个可缓存大小的线程池
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < NUM_OF_PROPOSER; i++) {
            Proposer proposer = new Proposer(i, i + "#Proposer", NUM_OF_PROPOSER, acceptors);
            //为每一个提议者分配一条线程
            es.submit(proposer);
        }
        //回收线程
        es.shutdown();
    }
}
