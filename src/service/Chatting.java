package service;

import component.ReceiverThread;
import component.SenderThread;
import util.MyIp;

import java.net.DatagramSocket;
import java.util.Scanner;

public class Chatting {
    private static final int BUFFER_SIZE = 4096; // 패킷 크기

    public void chatting() {
        Scanner scanner = new Scanner(System.in);

        try {
            // 로컬 IP 확인
            String myIp = new MyIp().getLocalIp();
            System.out.println("나의 IP 주소: " + myIp);

            // 포트 번호 설정
            System.out.print("내가 사용할 포트번호를 입력해주세요 : ");
            int myPort = scanner.nextInt();
            scanner.nextLine();

            // 상대방 IP와 포트 입력
            System.out.print("상대방의 아이피 주소를 입력해주세요 : ");
            String peerIP = scanner.nextLine();

            System.out.print("상대방의 포트번호를 입력해주세요 : ");
            int peerPort = scanner.nextInt();
            scanner.nextLine();

            // 사용자 이름 입력
            System.out.print("사용자 이름을 입력해주세요: ");
            String senderName = scanner.nextLine();

            // 소켓 생성
            DatagramSocket socket = new DatagramSocket(myPort);

            // 수신 스레드 실행
            Thread receiverThread = new Thread(new ReceiverThread(socket, BUFFER_SIZE));
            receiverThread.start();

            // 송신 스레드 실행
            Thread senderThread = new Thread(new SenderThread(socket, peerIP, peerPort, senderName));
            senderThread.start();

            // 송신 스레드 종료 대기
            senderThread.join();

            // 소켓 닫기
            socket.close();
        } catch (Exception e) {
            System.err.println("에러: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
