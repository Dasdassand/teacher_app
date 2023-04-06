package ru.vsu.app.teacher.Server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.app.teacher.entity.Test;
import ru.vsu.app.teacher.tempory.TMPData;

import java.util.ArrayList;
import java.util.List;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    private static final List<PersonChanel> channels = new ArrayList<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx){
        System.out.println("Клиент подключился: " + ctx);
        channels.add(new PersonChanel(null, ctx.channel(), ctx));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Клиент " + ctx.channel() + " получил ошибку - " + cause.getMessage());
        try {
            channels.remove(ctx.channel());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if (s.split(" ")[0].equals("name")) {
            for (PersonChanel personChanel :
                    channels) {
                if (personChanel.channel.equals(channelHandlerContext.channel())) {
                    personChanel.name = s.split(" ")[2];
                    TMPData.sendTest.setStudents(personChanel);
                } else {
                    saveResult(s);
                }
            }
        }
    }

    public static void sendTest(String name, Test test) {
        for (PersonChanel personChanel :
                channels) {
            if (personChanel.name.equals(name)) {
                personChanel.channelHandlerContext.writeAndFlush(test);
            }
        }
    }

    private void saveResult(String channel) {
        System.out.println(channel);
    }

    @Data
    @AllArgsConstructor
    public static class PersonChanel {
        private String name;
        private Channel channel;
        private ChannelHandlerContext channelHandlerContext;
    }

}
