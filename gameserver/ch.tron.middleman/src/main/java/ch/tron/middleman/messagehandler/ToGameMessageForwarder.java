package ch.tron.middleman.messagehandler;

import ch.tron.middleman.messagedto.InAppMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ToGameMessageForwarder implements InAppMessageForwarder {

    @Override
    public void forwardMessage(InAppMessage msg) {
        try {
            Class<?> GameManager = Class.forName("ch.tron.game.GameManager");
            Method handleMessage = GameManager.getDeclaredMethod("handleInAppIncomingMessage", InAppMessage.class);
            handleMessage.invoke(null, msg);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
