package ru.vsu.app.teacher.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import ru.vsu.app.teacher.controllers.test.entity.TestSend;
import ru.vsu.app.teacher.entity.Quest;
import ru.vsu.app.teacher.repository.TeacherRepository;
import ru.vsu.app.teacher.tempory.TMPData;

import java.util.ArrayList;
import java.util.List;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    private static final List<PersonChanel> channels = new ArrayList<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Клиент подключился: " + ctx);
        channels.add(new PersonChanel(null, null, ctx.channel(), ctx));
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Клиент " + ctx.channel() + " получил ошибку - " + cause.getMessage());
        try {
            int tmp = 0;
            for (int i = 0; i < channels.size(); i++) {
                if (channels.get(i).channelHandlerContext.equals(ctx)) {
                    TMPData.sendTest.setStudents(channels.get(i), true);
                    channels.remove(i);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if (s.split(" ")[0].equals("name")) {
            TeacherRepository repository = new TeacherRepository();
            var ch = repository.getResultSet("Select id From student Where name = " + "'" + s.split(" ")[1] + "'"
                    + "AND password = " + "'" + s.split(" ")[3] + "'");
            var id = -1;
            while (ch.next()) {
                id = ch.getInt(1);
            }
            if (id != -1) {
                channelHandlerContext.writeAndFlush("true");
                for (PersonChanel personChanel :
                        channels) {
                    if (personChanel.channel.equals(channelHandlerContext.channel())) {
                        personChanel.name = s.split(" ")[1];
                        personChanel.id = String.valueOf(id);
                        TMPData.sendTest.setStudents(personChanel, true);
                    }
                }

            }
            if (TMPData.flagSend) {
                sendTestPlatoon(channelHandlerContext);
            }
        } else {
            saveResult(s);
        }
    }

    public static void sendTest(String id, TestSend test, int version) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var finalTest = new Test(test.getQuest().get(version), test.getTime());
        System.out.println(finalTest);
        for (PersonChanel personChanel :
                channels) {
            if (personChanel.id.equals(id.split(" ")[0])) {
                personChanel.channelHandlerContext.writeAndFlush(mapper.writeValueAsString(finalTest));
            }
        }
    }

    private void saveResult(String channel) {
        System.out.println(channel);
    }

    @Data
    @AllArgsConstructor
    public static class PersonChanel {
        private String id;
        private String name;
        private Channel channel;
        private ChannelHandlerContext channelHandlerContext;
    }

    @Data
    @ToString
    public static class Test {
        private final List<Quest> quest;
        private final String time;

        public Test(List<Quest> quest, String time) {
            this.quest = quest;
            this.time = time;
        }
    }

    private void sendTestPlatoon(ChannelHandlerContext context) throws JsonProcessingException {
        if (!TMPData.studentTest.isEmpty()) {
            if (!TMPData.flagSend) {
                return;
            } else {
                for (PersonChanel channel : channels) {
                    if (channel.getChannelHandlerContext().equals(context)) {
                        var index = 0;
                        for (int j = 0; j < TMPData.studentTest.size(); j++) {
                            if (TMPData.studentTest.get(j).getStudentID().equals(channel.name)) {
                                index = j;
                            }
                        }
                        sendTest(channel.id + " " + channel.name, TMPData.studentTest.get(index).getTestSend(),
                                TMPData.studentTest.get(index).getVersion());
                    }
                }
            }
        } else sendTestPlatoon(context);

    }
}
