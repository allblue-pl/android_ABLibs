package pl.allblue.ablibs.abapi;

import org.json.JSONObject;

public class ABApiResult extends ABApiResult_Base {
    private JSONObject json;

    public ABApiResult(int result, String message, JSONObject json) {
        super(result, message);

        this.json = json;
    }

    public JSONObject getData() {
        return this.json;
    }
}
