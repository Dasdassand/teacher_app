package ru.vsu.app.teacher.network;
/**
import com.example.testmaker.controller.OtherController;
import com.example.testmaker.data.DataBaseAPI;
import com.example.testmaker.data.TemporaryMemory;
import com.example.testmaker.entety.*;
import com.example.testmaker.security.Security;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    private final List<PersonChanel> channels = new ArrayList<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Клиент подключился: " + ctx);
        channels.add(new PersonChanel(null, ctx.channel().toString()));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Клиент " + ctx.channel() + " получил ошибку - " + cause.getMessage());
        channels.remove(ctx.channel());
       // ctx.close();
    }

    private TestTransfer makeTest(String name, String password, String channel) throws IOException, SQLException {
        User user = new User(name, password);
        for (int i = 0; i < TemporaryMemory.tests.size(); i++) {
            if (TemporaryMemory.tests.get(i).getUser().equals(user)) {
                channels.get(getIndex(channel)).setUser(TemporaryMemory.tests.get(i).getUser());
                return TemporaryMemory.tests.get(i).getTests().get(0) == null ? null : TestTransfer.mapper(TemporaryMemory.tests.get(i).getTests().get(0));
            }
        }
        return null;
    }

    private int getIndex(String channel) {
        for (int i = 0; i < channels.size(); i++) {
            if (channels.get(i).getChannel().equals(channel))
                return i;
        }
        return -1;
    }

    /**
    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        String[] tmp = s.split("!0!");
        if (tmp[0].equals("auth")) {
             test = makeTest(tmp[1], tmp[2], channelHandlerContext.channel().toString());
            if (test == null) {
                channelHandlerContext.writeAndFlush("false");
            } else {
                channelHandlerContext.writeAndFlush(new ObjectMapper().writeValueAsString(test));
            }
        } else {
            saveResult(s, channelHandlerContext.channel().toString());
        }
    }

    private void saveResult(String data, String channel) throws IOException {
        User user = channels.get(getIndex(channel)).getUser();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Answers> answers = objectMapper.readValue(data, objectMapper.getTypeFactory().constructCollectionType(List.class, Answers.class));
        System.out.println(answers);
        TemporaryMemory.personResult.add(new PersonResult(user, answers));
    }

}
 */