package com.goldwiond.ep.datatransfer;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan({"com.goldwiond.ep.datatransfer.dao"})
public class DataTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataTransferApplication.class, args);
    }

//    @Override
//    public void run(String... args) throws Exception {
//        SocketServer.startServer(4512, new DataHandler(false));
//    }

//    @Override
//    public void run (String... args) throws Exception{
//        ServerSocket serverSocket = null;
//        Socket socket = null;
//        try {
//            //建立服务器的Socket，并设定一个监听的端口PORT
//            serverSocket = new ServerSocket(4564);
//            //由于需要进行循环监听，因此获取消息的操作应放在一个while大循环中
//            while(true){
//                try {
//                    //建立跟客户端的连接
//                    socket = serverSocket.accept();
//                } catch (Exception e) {
//                    System.out.println("建立与客户端的连接出现异常");
//                    e.printStackTrace();
//                }
//                ServerThread thread = new ServerThread(socket);
//                thread.start();
//            }
//        } catch (Exception e) {
//            System.out.println("端口被占用");
//            e.printStackTrace();
//        }
//        finally {
//            serverSocket.close();
//        }
//    }

}
