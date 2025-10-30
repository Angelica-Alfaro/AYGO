package com.example.demo;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ObjectMessage;
import org.jgroups.Receiver;
import org.jgroups.View;
import java.util.List;
import java.io.OutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.io.DataOutputStream;
import java.io.DataInputStream;
import org.jgroups.util.Util;
import org.springframework.stereotype.Service;

@Service
public class SimpleChat implements Receiver {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final List<String> state=new LinkedList<String>();

    public SimpleChat() throws Exception {
        channel=new JChannel().setReceiver(this);
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
    }

    public void messageEvent(String line) {
        try{
            System.out.print(">");
            line="[" + user_name + "] " + line;
            System.out.flush();
            Message msg=new ObjectMessage(null, line);
            channel.send(msg);
        }
        catch(Exception e) {
        }
    }

    public void openChat() throws Exception {
        channel.connect("ChatCluster");
        channel.getState(null, 10000);
    }

    public void closeChat() {
        channel.close();
    }

    @Override
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    @Override
    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);
        synchronized(state) {
            state.add(line);
        }
    }
    
    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        List<String> list;
        list=(List<String>)Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.clear();
            state.addAll(list);
        }
        System.out.println(list.size() + " messages in chat history):");
        list.forEach(System.out::println);
    }
}