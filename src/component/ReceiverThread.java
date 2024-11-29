package component;

import model.ChatMessage;
import util.SerializationUtil;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiverThread implements Runnable {
    private final DatagramSocket socket;
    private final int bufferSize;

    public ReceiverThread(DatagramSocket socket, int bufferSize) {
        this.socket = socket;
        this.bufferSize = bufferSize;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[bufferSize];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                // 역직렬화하여 메시지 객체 복원
                ChatMessage message = (ChatMessage) SerializationUtil.deserialize(packet.getData());
                System.out.println("\n["+message.getSender()+"]: " + message.getMessage());
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("메시지를 받는 도중 문제가 발생했습니다: " + e.getMessage());
                break;
            }
        }
    }
}