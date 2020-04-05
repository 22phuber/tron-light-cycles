package ch.tron.game.config;

import org.json.JSONException;
import org.json.JSONObject;

public enum CanvasConfig {
    WIDTH(400),
    HEIGHT(400);

    private int canvasConfig;

    CanvasConfig(int canvasConfig) {
        this.canvasConfig = canvasConfig;
    }

    public int value() {
        return canvasConfig;
    }

    public static JSONObject getCanvasConfig() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("subject", "canvas webserverconfig");
            jo.put("width", WIDTH.value());
            jo.put("height", HEIGHT.value());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo;
    }
}
