package component;

import model.ChatMessage;
import util.SerializationUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class SenderThread implements Runnable {
    private final DatagramSocket socket;
    private final String peerIP;
    private final int peerPort;
    private final String senderName;

    public SenderThread(DatagramSocket socket, String peerIP, int peerPort, String senderName) {
        this.socket = socket;
        this.peerIP = peerIP;
        this.peerPort = peerPort;
        this.senderName = senderName;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("채팅을 시작합니다. ('exit' 입력 시 종료):");

        while (true) {
            System.out.print("[ 나 ] : ");
            String messageText = scanner.nextLine();

            if (messageText.equalsIgnoreCase("exit")) {
                System.out.println("채팅을 종료합니다...");
                sendMessage("상대방이 채팅을 종료 했습니다.");
                break;
            }
            sendMessage(messageText);
        }
        scanner.close();
    }
    private void sendMessage(String messageText) {
        try {
            ChatMessage message = new ChatMessage(senderName, messageText);
            byte[] data = SerializationUtil.serialize(message);

            // 패킷 전송
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(peerIP), peerPort);
            socket.send(packet);
        } catch (IOException e) {
            System.err.println("메시지 전송 중 에러가 발생했습니다: " + e.getMessage());
        }
    }
}