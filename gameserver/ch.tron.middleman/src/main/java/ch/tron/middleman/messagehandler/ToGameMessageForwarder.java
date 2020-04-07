package ch.tron.middleman.messagehandler;

import ch.tron.middleman.messagedto.InAppMessage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Implements a service to send {@link InAppMessage} from
 * {@link ch.tron.transport} to {@link ch.tron.game} using the
 * {@link java.lang.reflect} API.
 */
public class ToGameMessageForwarder implements InAppMessageForwarder {

    @Override
    public void forwardMessage(InAppMessage msg) {
        try {
            Class<?> GameManager = Class.forName("ch.tron.game.GameManager");
            Method handleMessage = GameManager.getDeclaredMethod("handleInAppIncomingMessage", InAppMessage.class);
            handleMessage.invoke(null, msg);
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.getCause();
        }
    }
}
