import service.Chatting;
import util.MyIp;
import component.ReceiverThread;
import component.SenderThread;

import java.net.*;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        Chatting chatting = new Chatting();
        chatting.chatting();
    }
}